package com.wottui.edw.plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 一个事务中所有资源管理器。
 *
 * @author chendonglin
 * @since 1.0.0-SNAPSHOT
 */
public class TransactionSourcesManager {

    /**
     * 当前线程在这个事务中执行的SQL
     */
    private static ThreadLocal<Set<String>> sqlThreadLocal = ThreadLocal.withInitial(() -> new HashSet<>(8));

    /**
     * 标记当前事务是否已经发过事件，避免同一个事务发送多次事件。
     */
    private static ThreadLocal<Boolean> markIfSendEventThreadLocal = new ThreadLocal<>();

    /**
     * 记录当前事务涉及有哪些表，哪些主键ID 发生了变化
     */
    private static ThreadLocal<Map<String, Set<Long>>> tableIdSetThreadLocal = ThreadLocal.withInitial(() -> new HashMap<>(8));


    /**
     * 判断是否已发送过事件
     *
     * @return true-已经发送过,false 没有发送过
     */
    public static boolean hasMarkSendEvent() {
        return markIfSendEventThreadLocal.get() != null;
    }

    /**
     * 标记已经发送过事件
     */
    public static void markHasSendEvent() {
        markIfSendEventThreadLocal.set(Boolean.TRUE);
    }

    /**
     * 绑定transactionSource
     *
     * @param source 事务资源
     */
    public static void bind(TransactionSource source) {
        if (source == null) {
            return;
        }
        String sql = source.getSql();

        sqlThreadLocal.get().add(sql);

        String table = source.getTable();
        Long id = source.getId();

        Map<String, Set<Long>> tableIdSetMap = tableIdSetThreadLocal.get();
        Set<Long> idsSet;
        if (tableIdSetMap.containsKey(table)) {
            idsSet = tableIdSetMap.get(table);
        } else {
            idsSet = new HashSet<>(2);
            tableIdSetMap.put(table, idsSet);
        }
        idsSet.add(id);
    }

    /**
     * 解除绑定
     */
    public static void unBind() {
        sqlThreadLocal.remove();
        markIfSendEventThreadLocal.remove();
        tableIdSetThreadLocal.remove();
    }

}
