package com.qiqi.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author QiQiDream
 */
@Getter
public enum MenuTypeEnum {
    /**
     * 目录,菜单,按钮
     */
    FOLDER(1,"目录"),
    MENU(2,"菜单"),
    BUTTON(3,"按钮");

    MenuTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }
    private final int code;

    @JsonValue
    @EnumValue
    private final String type;
}
