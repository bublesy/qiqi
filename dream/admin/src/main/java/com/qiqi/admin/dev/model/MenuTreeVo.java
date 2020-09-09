package com.qiqi.admin.dev.model;

import com.qiqi.common.util.tree.DataTree;
import lombok.Data;

import java.util.List;

/**
 * @author QiQiDream
 * @since 2019/12/5 15:07
 */
@Data
public class MenuTreeVo implements DataTree<MenuTreeVo> {
    private Long id;

    private Long parentId;

    private String title;

    private List<MenuTreeVo> children;
}
