package com.qiqi.basicdata.dto;

import com.qiqi.basicdata.entity.CustomerQuotationDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-15
 */
@Data
public class CustomerQuotationDTO extends CustomerQuotationDO {
    private Long page;
    private Long count;
}
