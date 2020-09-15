package com.qiqi.admin.supplier.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author sun
 * @time 2020/9/15 11:45
 */
@Data
public class PaperboardDataSettingVO {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 楞型
     */
    @TableField("ridgeType")
    private String ridgeType;

    /**
     * 描述
     */
    private String represent;
    private Long id;
}
