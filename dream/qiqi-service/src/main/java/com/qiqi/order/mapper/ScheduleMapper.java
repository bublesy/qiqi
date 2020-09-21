package com.qiqi.order.mapper;

import com.qiqi.order.entity.ScheduleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 排期表 Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
public interface ScheduleMapper extends BaseMapper<ScheduleDO> {

    void delete(@Param("id") Long id);
}
