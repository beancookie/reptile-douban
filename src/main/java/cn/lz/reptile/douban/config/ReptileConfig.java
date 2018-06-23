package cn.lz.reptile.douban.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author luzhong
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "reptile-config")
public class ReptileConfig {
    private Integer retryTimes;
    private Integer timeOut;
    private Integer sleepTime;
    private Integer threadNumber;
    private String redisHost;
    private String startUrl;
}
