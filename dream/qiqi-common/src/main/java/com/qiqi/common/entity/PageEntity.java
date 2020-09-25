package com.qiqi.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author QiQiDream
 * @since 2019/12/18 14:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageEntity<T> {

    @ApiModelProperty(value = "总数量")
    private Integer total;

    @ApiModelProperty(value = "当前页对象")
    private List<T> list;

    private Map<String,Object> map;

    public PageEntity(long total, List<T> list) {
        this.total = Integer.parseInt(total+"");
        this.list = list;
    }

    public PageEntity(long total, Map<String,Object> map) {
        this.total = Integer.parseInt(total+"");
        this.map = map;
    }
}
