package com.qiqi.admin.dev.model;

import lombok.Data;

import java.util.List;

/**
 * @author QiQiDream
 * @date 2020-06-12 11:20
 */
@Data
public class DevUserVO {

    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 账号
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色名称
     */
    private List<String> roleNames;


    /**
     * 角色id
     */
    private List<Long> roleIds;


}
