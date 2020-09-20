package com.qiqi.admin.material.dto;

import com.qiqi.material.entity.MaterialDataDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-19
 */
@Data
public class MaterialDataDTO extends MaterialDataDO {

    private Long page;
    private Long count;
}
