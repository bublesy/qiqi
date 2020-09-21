package com.qiqi.order.dto;

import com.qiqi.order.entity.CombinationDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-18
 */
@Data
public class CombinationDTO extends CombinationDO {
    private Long page;
    private Long count;
}
