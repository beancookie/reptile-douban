package cn.lz.reptile.douban.core;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Log4j
public class FirstPageProcessorTest {

    @Autowired
    private FirstPageProcessor firstPageProcessor;

    @Test
    public void start() {
    }
}