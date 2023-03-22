package com.liujian.springbootinit.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局搜索类型枚举
 */
public enum SearchEnum {
    POST("文章", "post"),
    USER("用户", "user"),
    PICTURE("图片", "picture"),
    All("全部","all");

    private final String text;

    private final String value;
    SearchEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }
    /**
     * 获取值列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }
    /**
     * 根据 value 获取枚举
     */
    public static SearchEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (SearchEnum anEnum : SearchEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
