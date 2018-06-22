package cn.lz.reptile.douban.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * @author larobyo
 */
@Document(indexName = "douban", type = "films")
@Data
public class Film {
    @Id
    private String title;

    private Float ratingNumbers;

    private Integer commentNumbers;

    private String introduction;

    private String doubanUrl;

    private List<String> hotComment = new LinkedList<>();

    private List<String> director = new LinkedList<>();

    private List<String> scriptwriter = new LinkedList<>();

    private List<String> act = new LinkedList<>();

    private List<String> category = new LinkedList<>();

    private List<String> award = new LinkedList<>();

    private Integer showYear;

    private Integer showMonth;

    private Integer length;

    private List<String> area;

    public void addDirector(String director) {
        this.director.add(director);
    }

    public void addScriptwriter(String scriptwriter) {
        this.scriptwriter.add(scriptwriter);
    }

    public void addAct(String act) {
        this.act.add(act);
    }

    public void addCategory(String category) {
        this.category.add(category);
    }

    public void addHotComment(String comment) {
        this.hotComment.add(comment);
    }

    public void addAward(String award) {
        this.award.add(award);
    }
}
