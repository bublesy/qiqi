package com.qiqi.generator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.qiqi.common.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 人事档案管理
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("personnel")
public class PersonnelDO extends BaseModel {

    private static final long serialVersionUID=1L;

    private String no;

    private String name;

    private Boolean sex;

    @TableField("onJob")
    private Boolean onJob;

    @TableField("entryDate")
    private Date entryDate;

    private String dept;

    private String position;


}
