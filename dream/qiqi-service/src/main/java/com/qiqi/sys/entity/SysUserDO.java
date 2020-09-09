package com.qiqi.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author QiQiDream
 * @date 2020-06-12 11:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(autoResultMap = true,value = "sys_user")
public class SysUserDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 账号
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;


    /**
     * 头像
     */
    private String avatar;



}
