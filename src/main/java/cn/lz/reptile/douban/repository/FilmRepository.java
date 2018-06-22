package cn.lz.reptile.douban.repository;

import cn.lz.reptile.douban.entity.Film;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author luzhong
 */
@Repository
public interface FilmRepository extends ElasticsearchRepository<Film, String> {
}
