package com.wottui.edw.plugin;

/**
 * <p>
 * <b>创建日期：</b> 2019/6/6
 * </p>
 *
 * @author chendonglin
 * @since 1.0.0-SNAPSHOT
 */
public interface SqlTranslator {

    TranslatorMap translate(String updateSql);

    TranslatorMap translate(Object insertObj);
}
