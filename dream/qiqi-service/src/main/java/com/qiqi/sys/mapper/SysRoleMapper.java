package com.qiqi.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiqi.sys.entity.SysRoleDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-06-29
 */
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {
    /**
     * 根据用户id获取角色编码
     * @param userId 用户id
     * @return
     */
    @Select("SELECT a.code FROM sys_role a,sys_user_role b where a.is_deleted = 0 AND a.id = b.role_id AND b.user_id = #{userId}")
    List<String> getRoleCodeByUserId(Long userId);

    /**
     * 根据用户id获取角色id
     * @param userId 用户id
     * @return
     */
    @Select("SELECT a.id FROM sys_role a,sys_user_role b where a.is_deleted = 0 AND a.id = b.role_id AND b.user_id = #{userId}")
    List<Long> getRoleIdByUserId(Long userId);
}
