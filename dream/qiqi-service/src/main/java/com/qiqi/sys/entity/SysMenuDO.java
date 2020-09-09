package com.qiqi.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author QiQiDream
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenuDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 类型（目录,菜单,按钮）
     */
    private Integer type;

    /**
     * 标题
     */
    private String title;

    /**
     * 菜单编码（开启缓存时,需要与组件名一致，不可重复）
     */
    private String code;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 重定向地址，在面包屑中点击会重定向去的地址
     */
    private String redirect;

    /**
     * 是否隐藏（左侧路由不显示）
     */
    @TableField("is_hidden")
    private Boolean hidden;

    /**
     * 是否缓存
     */
    @TableField("is_cache")
    private Boolean cache;

    /**
     * 排序
     */
    private Integer sort;


}
