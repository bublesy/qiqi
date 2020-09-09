package com.qiqi.sys.service.impl;

import com.qiqi.sys.entity.SysRoleDO;
import com.qiqi.sys.mapper.SysRoleMapper;
import com.qiqi.sys.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-06-29
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements SysRoleService {

    @Override
    public List<String> getRoleCodeByUserId(Long userId) {
        return baseMapper.getRoleCodeByUserId(userId);
    }
}
