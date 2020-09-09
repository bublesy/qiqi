package com.qiqi.sys.model;

import com.qiqi.common.util.tree.DataTree;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单视图层
 * @author QiQiDream
 * @since 2019/12/2 9:11
 */
@Data
public class MenuVo implements DataTree<MenuVo> {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "父级id")
    private Long parentId;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "重定向地址,在面包屑中点击会重定向去的地址")
    private String redirect;

    @ApiModelProperty(value = "菜单编码（开启缓存时,需要与组件名一致，不可重复）")
    private String code;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "是否不在左侧路由显示")
    private Boolean hidden;

    @ApiModelProperty(value = "是否缓存")
    private Boolean cache;

    @ApiModelProperty(value = "类型（'目录','菜单','按钮'）")
    private Integer type;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "子菜单")
    List<MenuVo> children;
}
