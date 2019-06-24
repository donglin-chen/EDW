package com.wottui.edw.plugin;

/**
 * 事务资源对象
 *
 * @author chendonglin
 * @since 1.0.0-SNAPSHOT
 */
public class TransactionSource {

    /**
     * 执行SQL
     */
    private String sql;

    /**
     * 执行table
     */
    private String table;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 原始值
     */
    private Object original;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getOriginal() {
        return original;
    }

    public void setOriginal(Object original) {
        this.original = original;
    }
}
