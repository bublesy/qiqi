package com.qiqi.order.dto;

import com.qiqi.order.entity.ColourDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-18
 */
@Data
public class ColourDTO extends ColourDO {
    private Long page;
    private Long count;
}
