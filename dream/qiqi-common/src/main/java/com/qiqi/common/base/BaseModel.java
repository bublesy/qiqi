package com.qiqi.common.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author QiQiDream
 * @since  2019/12/4 17:28
 */
@Getter
@Setter
public class BaseModel implements Serializable {

    /** 唯一id */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 逻辑删除标识 */
    @TableField(value = "is_deleted",fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    /** 更新时间 */
    @TableField(value = "updated_time",fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    /** 更新人 */
    @TableField(value = "updated_by",fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    /** 创建时间 */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    /** 创建者 */
    @TableField(value = "created_by",fill = FieldFill.INSERT)
    private Long createdBy;
}
