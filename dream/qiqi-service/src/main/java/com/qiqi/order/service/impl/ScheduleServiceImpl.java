package com.qiqi.order.service.impl;

import com.qiqi.order.entity.ScheduleDO;
import com.qiqi.order.mapper.ScheduleMapper;
import com.qiqi.order.service.ScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 排期表 服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, ScheduleDO> implements ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public void delete(Long scheduleId) {
        scheduleMapper.delete(scheduleId);
    }
}
