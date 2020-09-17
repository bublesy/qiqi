package com.qiqi.order.dto;

import com.qiqi.order.entity.ScheduleDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-17
 */
@Data
public class ScheduleDTO extends ScheduleDO {

    private Long page;
    private Long count;
}
