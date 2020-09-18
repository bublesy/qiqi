package com.qiqi.order.dto;

import com.qiqi.order.entity.UniteDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-18
 */
@Data
public class UniteDTO extends UniteDO {
    private Long page;
    private Long count;
}
