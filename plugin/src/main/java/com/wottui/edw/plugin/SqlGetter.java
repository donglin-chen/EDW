package com.wottui.edw.plugin;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * <b>创建日期：</b> 2019/6/6
 * </p>
 *
 * @author chendonglin
 * @since 1.0.0-SNAPSHOT
 */
public class SqlGetter {

    private Invocation invocation;
    private MappedStatement mappedStatement;


    public SqlGetter(Invocation invocation) {
        this.invocation = invocation;
        this.mappedStatement = (MappedStatement) invocation.getArgs()[0];

    }

    boolean isUpdateCommand() {
        SqlCommandType commandType = mappedStatement.getSqlCommandType();
        return commandType == SqlCommandType.UPDATE;
    }

    String getSql() {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameterObject = invocation.getArgs()[1];
        parameterObject = wrapCollection(parameterObject);
        BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(parameterObject);
        Configuration configuration = mappedStatement.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) {
                        jdbcType = configuration.getJdbcTypeForNull();
                    }
                    sql = replaceParameter(sql, value, jdbcType, parameterMapping.getJavaType());
                }
            }
        }
        return sql;
    }

    private Object wrapCollection(final Object object) {
        if (object instanceof Collection) {
            DefaultSqlSession.StrictMap<Object> map = new DefaultSqlSession.StrictMap<>();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }
            return map;
        } else if (object != null && object.getClass().isArray()) {
            DefaultSqlSession.StrictMap<Object> map = new DefaultSqlSession.StrictMap<>();
            map.put("array", object);
            return map;
        }
        return object;
    }

    private static String replaceParameter(String sql, Object value, JdbcType jdbcType, Class javaType) {
        String strValue = String.valueOf(value);
        if (jdbcType != null) {
            switch (jdbcType) {
                //数字
                case BIT:
                case TINYINT:
                case SMALLINT:
                case INTEGER:
                case BIGINT:
                case FLOAT:
                case REAL:
                case DOUBLE:
                case NUMERIC:
                case DECIMAL:
                    break;
                //日期
                case DATE:
                case TIME:
                case TIMESTAMP:
                default:
                    strValue = "'" + strValue + "'";
            }
        } else if (Number.class.isAssignableFrom(javaType)) {
            //不加单引号
        } else {
            strValue = "'" + strValue + "'";
        }
        return sql.replaceFirst("\\?", strValue);
    }

    Object getInsertObject() {
        return invocation.getArgs()[1];
    }

}
