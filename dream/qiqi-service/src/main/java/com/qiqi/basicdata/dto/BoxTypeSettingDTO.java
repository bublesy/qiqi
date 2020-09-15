package com.qiqi.basicdata.dto;

import com.qiqi.basicdata.entity.BoxTypeSettingDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-15
 */
@Data
public class BoxTypeSettingDTO extends BoxTypeSettingDO {

    private Long page;
    private Long count;
}
