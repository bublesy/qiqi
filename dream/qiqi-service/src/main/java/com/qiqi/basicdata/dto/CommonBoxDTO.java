package com.qiqi.basicdata.dto;

import com.qiqi.basicdata.entity.CommonBoxDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-15
 */
@Data
public class CommonBoxDTO extends CommonBoxDO {

    private Long page;
    private Long count;
}
