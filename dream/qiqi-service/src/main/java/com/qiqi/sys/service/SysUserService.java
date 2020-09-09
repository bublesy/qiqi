package com.qiqi.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiqi.sys.entity.SysUserDO;
import com.qiqi.sys.model.UserInfoVo;

/**
 * @author QiQiDream
 * @since 2019/11/18 10:53
 */
public interface SysUserService extends IService<SysUserDO> {
    /**
     *  获取登录用户信息
     * @param sysUserDO 系统用户实体
     * @return
     */
    UserInfoVo getSysUserInfo(SysUserDO sysUserDO);
}
