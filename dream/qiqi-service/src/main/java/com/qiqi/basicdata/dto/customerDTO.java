package com.qiqi.basicdata.dto;

import com.qiqi.basicdata.entity.CustomerInformationDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-15
 */
@Data
public class customerDTO extends CustomerInformationDO {
    private Long page;
    private Long count;
}
