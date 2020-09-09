package com.qiqi.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiqi.sys.entity.SysMenuDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统菜单表 Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-06-29
 */
public interface SysMenuMapper extends BaseMapper<SysMenuDO> {
    /**
     * 根据多个角色id获取菜单
     * @param roleIds 角色id
     * @return
     */
    @Select({
            "<script>",
            "SELECT a.id,a.parent_id,a.type,a.title,a.code,a.component,a.path,a.icon,a.redirect,a.is_hidden as hidden,a.is_cache as cache,a.sort ",
            "FROM sys_menu a,sys_role_menu b where a.is_deleted = 0 AND a.id = b.menu_id AND b.role_id in",
            "<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>#{roleId}</foreach>",
            "</script>"
    })
    List<SysMenuDO> getMenuByRoleIds(@Param("roleIds")List<Long> roleIds);

}
