package com.wottui.edw.example;

import com.wottui.edw.plugin.MybatisExecutorSqlSender2MqPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * <p>
 * <b>创建日期：</b> 2019/6/6
 * </p>
 *
 * @author chendonglin
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
public class MybatisExecutorSqlGetterSpringCfg {


    @Bean
    public MybatisExecutorSqlSender2MqPlugin sqlStatsInterceptor() {
        MybatisExecutorSqlSender2MqPlugin sqlStatsInterceptor = new MybatisExecutorSqlSender2MqPlugin();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        sqlStatsInterceptor.setProperties(properties);
        return sqlStatsInterceptor;
    }

}
