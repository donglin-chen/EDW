package com.wottui.edw.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * <p>
 * <b>创建日期：</b> 2019/6/6
 * </p>
 *
 * @author chendonglin
 * @since 1.0.0-SNAPSHOT
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisExecutorSqlSender2MqPlugin implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisExecutorSqlSender2MqPlugin.class);
    private static final SqlTranslator SQL_TRANSLATOR = new SqlTranslatorImpl();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        SqlGetter getter = new SqlGetter(invocation);
        Object object = invocation.proceed();
        try {
            TranslatorMap map;
            if (getter.isUpdateCommand()) {
                String sql = getter.getSql();
                map = SQL_TRANSLATOR.translate(sql);
            } else {
                Object insertObj = getter.getInsertObject();
                map = SQL_TRANSLATOR.translate(insertObj);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }

        return object;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


}
