package com.qiqi.admin.material.dto;

import com.qiqi.material.entity.MaterialInStoreDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-19
 */
@Data
public class MaterialInStoreDTO extends MaterialInStoreDO {
    private Long page;
    private Long count;
}
