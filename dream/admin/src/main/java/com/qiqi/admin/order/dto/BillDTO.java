package com.qiqi.admin.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author hc
 * @desc
 * @since 2020-09-22
 */
@Data
public class BillDTO {
    private Long page;
    private Long count;
    private String name;

    private String deliveryDate;
}
