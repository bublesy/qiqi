package com.qiqi.order.dto;

import com.qiqi.order.entity.PrintLayoutDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-18
 */
@Data
public class PrintLayoutDTO extends PrintLayoutDO {
    private Long page;
    private Long count;
}
