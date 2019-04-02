package com.tools.base.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志枚举类
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2018/12/13 21:12
 */
public enum OperActionEnum {

    /**
     * 登录操作
     */
    LOGIN(1, "登录"),
    /**
     * 新增操作
     */
    ADD(2, "新增"),
    /**
     * 修改操作
     */
    MODIFY(3, "修改"),
    /**
     * 删除操作
     */
    DELETE(4, "删除"),
    /**
     * 导出操作
     */
    EXPORT(5, "导出"),
    /**
     * 授权操作
     */
    GRANT(6, "授权"),
    /**
     * 审核操作
     */
    AUDIT(7, "审核");

    private static final Map<Integer, String> STRING_TO_ENUM = new HashMap<Integer, String>();

    static {
        for (OperActionEnum blah : values()) {
            STRING_TO_ENUM.put(blah.getIndex(), blah.getText());
        }
    }

    private int index;
    private String text;

    OperActionEnum(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public static String fromIndex(Integer index) {
        return STRING_TO_ENUM.get(index);
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return text;
    }
}
