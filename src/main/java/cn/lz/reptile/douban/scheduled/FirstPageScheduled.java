package cn.lz.reptile.douban.scheduled;

import cn.lz.reptile.douban.config.Contents;
import cn.lz.reptile.douban.core.FirstPageProcessor;
import cn.lz.reptile.douban.pipeline.ElasticsearchPipeline;
import cn.lz.reptile.douban.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisPriorityScheduler;

/**
 * @author luzhong
 */
@Component
public class FirstPageScheduled {
    @Value("url")
    private String url;

    @Autowired
    ElasticsearchPipeline elasticsearchPipeline;

    @Autowired
    FilmRepository filmRepository;

    @Scheduled(fixedDelay = 5_000_000L)
    public void start() {
        Spider.create(new FirstPageProcessor(filmRepository))
                .addUrl(Contents.START_URL)
                .setScheduler(new RedisPriorityScheduler(Contents.REDIS_HOST))
                .addPipeline(elasticsearchPipeline)
                .thread(Contents.THREAD_NUMBER)
                .run();
    }
}
