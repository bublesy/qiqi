package com.qiqi.admin.staff.dto;

import com.qiqi.staff.entity.DeptDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-19
 */
@Data
public class DeptDTO extends DeptDO {
    private Long page;
    private Long count;
}
