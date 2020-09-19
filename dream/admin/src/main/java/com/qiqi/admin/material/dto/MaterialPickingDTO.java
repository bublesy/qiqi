package com.qiqi.admin.material.dto;

import com.qiqi.material.entity.MaterialPickingDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-19
 */
@Data
public class MaterialPickingDTO extends MaterialPickingDO {

    private Long page;
    private Long count;
}
