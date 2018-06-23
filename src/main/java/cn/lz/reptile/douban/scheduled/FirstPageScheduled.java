package cn.lz.reptile.douban.scheduled;

import cn.lz.reptile.douban.config.ReptileConfig;
import cn.lz.reptile.douban.core.FirstPageProcessor;
import cn.lz.reptile.douban.pipeline.ElasticsearchPipeline;
import cn.lz.reptile.douban.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisPriorityScheduler;

/**
 * @author luzhong
 */
@Component
public class FirstPageScheduled {
    @Autowired
    private ReptileConfig reptileConfig;

    @Autowired
    ElasticsearchPipeline elasticsearchPipeline;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    private FirstPageProcessor firstPageProcessor;

    @Scheduled(fixedDelay = 5_000_000L)
    public void start() {
        int i;
        Spider.create(firstPageProcessor)
                .addUrl(reptileConfig.getStartUrl())
                .setScheduler(new RedisPriorityScheduler(reptileConfig.getRedisHost()))
                .addPipeline(elasticsearchPipeline)
                .thread(reptileConfig.getThreadNumber())
                .run();
    }
}
