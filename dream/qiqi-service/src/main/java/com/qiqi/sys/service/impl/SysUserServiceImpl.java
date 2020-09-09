package com.qiqi.sys.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiqi.common.enums.MenuTypeEnum;
import com.qiqi.common.util.tree.TreeUtil;
import com.qiqi.sys.entity.SysMenuDO;
import com.qiqi.sys.entity.SysUserDO;
import com.qiqi.sys.mapper.SysMenuMapper;
import com.qiqi.sys.mapper.SysRoleMapper;
import com.qiqi.sys.mapper.SysUserMapper;
import com.qiqi.sys.model.MenuVo;
import com.qiqi.sys.model.UserInfoVo;
import com.qiqi.sys.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 自定义userDetailsService - 认证用户详情
 * @author QiQiDream
 * @since 2019/11/18 10:53
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {

    @Resource
    SysMenuMapper sysMenuMapper;
    @Resource
    SysRoleMapper sysRoleMapper;

    @Override
    public UserInfoVo getSysUserInfo(SysUserDO sysUserDO) {
        UserInfoVo userInfoVo = new UserInfoVo();
        List<Long> roleIds = sysRoleMapper.getRoleIdByUserId(sysUserDO.getId());
        List<SysMenuDO> allMenuList = ObjectUtils.isEmpty(roleIds)?new ArrayList<>():sysMenuMapper.getMenuByRoleIds(roleIds);


        List<String> permissions = allMenuList.stream().map(SysMenuDO::getCode).collect(toList());
        List<SysMenuDO> list = allMenuList.stream().filter(data -> data.getType() != MenuTypeEnum.BUTTON.getCode()).collect(toList());

        List<MenuVo> menus = TreeUtil.getTreeList(0L, Convert.convert(new TypeReference<List<MenuVo>>() {}, list));

        userInfoVo.setAvatar(sysUserDO.getAvatar());
        userInfoVo.setNickName(sysUserDO.getNickname());
        userInfoVo.setPermissions(permissions);
        userInfoVo.setMenus(menus);
        return userInfoVo;
    }
}
