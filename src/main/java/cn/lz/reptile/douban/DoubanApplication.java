package cn.lz.reptile.douban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author luzhong
 */
@SpringBootApplication
@EnableScheduling
public class DoubanApplication {
    public static void main(String[] args) {
        SpringApplication.run(DoubanApplication.class, args);
    }
}
