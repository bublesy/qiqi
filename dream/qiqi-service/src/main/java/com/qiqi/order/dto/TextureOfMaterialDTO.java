package com.qiqi.order.dto;

import com.qiqi.order.entity.TextureOfMaterialDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-18
 */
@Data
public class TextureOfMaterialDTO extends TextureOfMaterialDO {

    private Long page;
    private Long count;
}
