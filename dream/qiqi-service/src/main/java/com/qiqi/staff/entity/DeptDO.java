package com.qiqi.staff.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qiqi.common.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dept")
public class DeptDO extends BaseModel {

    private static final long serialVersionUID=1L;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 最后操作时间
     */
    private Date lastModifyTime;


}
