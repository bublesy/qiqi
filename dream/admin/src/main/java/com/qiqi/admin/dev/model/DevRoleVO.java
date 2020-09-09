package com.qiqi.admin.dev.model;

import lombok.Data;

import java.util.List;

/**
 * @author QiQiDream
 * @since 2020-06-29
 */
@Data
public class DevRoleVO {


    private Long id;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String remark;

    /**
     * 菜单id
     */
    private List<Long> menuIds;
}
