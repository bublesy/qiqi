package com.qiqi.admin.staff.dto;

import com.qiqi.staff.entity.PositionDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-19
 */
@Data
public class PositionDTO extends PositionDO {
    private Long page;
    private Long count;
}
