package cn.lz.reptile.douban.core;

import cn.lz.reptile.douban.config.Contents;
import cn.lz.reptile.douban.config.ReptileConfig;
import cn.lz.reptile.douban.entity.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author luzhong
 */
@Component
@Slf4j
public class FirstPageProcessor implements PageProcessor {
    @Autowired
    private ReptileConfig reptileConfig;

    @Bean
    public Site siteBean() {
        return Site.me()
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                .setRetryTimes(reptileConfig.getRetryTimes())
                .setTimeOut(reptileConfig.getTimeOut())
                .setSleepTime(reptileConfig.getSleepTime());
    }

    private static final Set<String> CATEGORIES = Stream.of("剧情,喜剧,动作,爱情,科幻,悬疑,惊悚,恐怖,犯罪,同性,音乐,歌舞,传记,历史,战争,西部,奇幻,冒险,灾难,武侠,情色".split(",")).collect(Collectors.toSet());

    @Override
    public void process(Page page) {
        final List<Film> films = new ArrayList<>();
        String url = page.getUrl().get();
        if (url.matches("https://movie\\.douban\\.com/subject/\\w+/")) {
            final Film film = new Film();
            try {
                Selectable content = page.getHtml().xpath("//div[@id='content']");
                film.setDoubanUrl(url);
                film.setTitle(content.xpath("//h1/span/text()").get().split(" ")[0]);
                Selectable info = content.xpath("//div[@id='info']");
                info.xpath("//span[1]/span[2]/a").nodes().forEach(node -> film.addDirector(node.xpath("//a/text()").get()));
                info.xpath("//span[2]/span[2]/a").nodes().forEach(node -> film.addScriptwriter(node.xpath("//a/text()").get()));
                info.xpath("//span[3]/span[2]/a").nodes().forEach(node -> film.addAct(node.xpath("//a/text()").get()));
                info.xpath("//span[@property='v:genre']").nodes().forEach(node -> {
                    String category = node.xpath("//span/text()").get();
                    if (CATEGORIES.contains(category)) {
                        film.addCategory(category);
                    }
                });
                film.setArea(Arrays.asList(info.regex(".制片国家/地区:</span>.+[\\u4e00-\\u9fa5]+.+[\\u4e00-\\u9fa5]+\\s+<br>").get().split("</span>")[1].split("<br>")[0].trim().split("/")));
                page.getHtml().xpath("//[@id='hot-comments']/div/div/p").nodes().forEach(node -> film.addHotComment(node.xpath("//p/text()").get()));
                page.getHtml().xpath("//div[@class='mod]/ul").nodes().forEach(node -> film.addAward(node.xpath("//ul/li[1]/a/text()").get() + "/" + node.xpath("//ul/li[2]/text()").get() + "/" + node.xpath("//ul/li[3]/text()").get()));
                film.setLength(Integer.parseInt(info.xpath("//span[@property='v:runtime']/text()").get().substring(0, 3).replaceAll("\\D", "")));
                film.setRatingNumbers(Float.parseFloat(content.xpath("//div[@id='interest_sectl']/div/div[2]/strong/text()").get()));
                film.setCommentNumbers(Integer.parseInt(content.xpath("//span[@property='v:votes']/text()").get()));
                String dateStr = info.xpath("//span[@property='v:initialReleaseDate']/text()").get();
                String year = dateStr.substring(0, 4);
                if (year.matches("\\d{4}")) {
                    film.setShowYear(Integer.parseInt(year));
                }
                String month = dateStr.substring(5, 7);
                if (month.matches("\\d{2}")) {
                    film.setShowMonth(Integer.parseInt(month));
                }
                film.setIntroduction(content.xpath("//div[@id='link-report']/span[1]/text()").get());
            } catch (RuntimeException e) {
                log.error("exception: {}", e.getMessage());
            }
            if (film.getTitle() != null && !"".equals(film.getTitle())) {
                log.info("film: {}", film.getTitle());
                films.add(film);
            }
        }
        page.putField(Contents.FILMS, films);
        page.addTargetRequests(page.getHtml().links().regex("https://movie\\.douban\\.com/top250\\?start=\\w+&filter=").all());
        page.addTargetRequests(page.getHtml().links().regex("https://movie\\.douban\\.com/subject/\\w+/").all());
    }

    @Override
    public Site getSite() {
        return siteBean();
    }
}
