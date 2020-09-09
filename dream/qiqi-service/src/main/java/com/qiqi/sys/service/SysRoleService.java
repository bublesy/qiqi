package com.qiqi.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiqi.sys.entity.SysRoleDO;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-06-29
 */
public interface SysRoleService extends IService<SysRoleDO> {
    /**
     * 根据用户id获取角色编码
     * @param userId 用户id
     * @return
     */
    List<String> getRoleCodeByUserId(Long userId);
}
