package com.qiqi.admin.staff.dto;

import com.qiqi.staff.entity.PersonnelDO;
import lombok.Data;

/**
 * @author hc
 * @since 2020-09-19
 */
@Data
public class PersonnerDTO extends PersonnelDO {
    private Long page;
    private Long count;
}
