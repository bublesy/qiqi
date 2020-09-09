package com.qiqi.generation;

import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * @author QiQiDream
 * @date 2020-07-02 13:42
 */
public enum DreamDbColumnType implements IColumnType {
    /**
     * JSONObject
     */
    FAST_JSON("JSONObject", "com.alibaba.fastjson.JSONObject");

    private final String type;
    private final String pkg;

    DreamDbColumnType(final String type, final String pkg) {
        this.type = type;
        this.pkg = pkg;
    }
    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getPkg() {
        return this.pkg;
    }
}
