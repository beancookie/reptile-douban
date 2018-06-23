package cn.lz.reptile.douban.pipeline;

import cn.lz.reptile.douban.config.Contents;
import cn.lz.reptile.douban.config.ReptileConfig;
import cn.lz.reptile.douban.entity.Film;
import cn.lz.reptile.douban.repository.FilmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author luzhong
 */
@Component
@Slf4j
public class ElasticsearchPipeline implements Pipeline {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Film> films = resultItems.get(Contents.FILMS);
        if (null != films && films.size() > 0) {
            filmRepository.saveAll(films);
        }
    }
}
