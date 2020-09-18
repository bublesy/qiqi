package com.qiqi.order.dto;

import com.qiqi.order.entity.NailsDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-18
 */
@Data
public class NailsDTO extends NailsDO {

    private Long page;
    private Long count;
}
