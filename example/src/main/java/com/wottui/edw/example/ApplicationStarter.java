package com.wottui.edw.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>
 * <b>创建日期：</b> 2019/6/6
 * </p>
 *
 * @author chendonglin
 * @since 1.0.0-SNAPSHOT
 */
@SpringBootApplication
@MapperScan("com.wottui.edw")
@PropertySource(value = "classpath:mybatis.properties")
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
