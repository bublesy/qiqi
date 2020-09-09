package com.qiqi.common.util.tree;

import java.util.List;

/**
 * 树形数据实体接口
 * @author QiQiDream
 * @since 2019/1/11 16:03
 */
public interface DataTree<T> {
    /**
     * 获取id
     * @author QiQiDream
     * @since 2019/5/15 10:28
     * @return java.lang.Long
     */
    Long getId();
    /**
     * 获取父id
     * @author QiQiDream
     * @since 2019/5/15 10:29
     * @return java.lang.Long
     */
    Long getParentId();
    /**
     * 设置子节点
     * @author QiQiDream
     * @since 2019/5/15 10:29
     * @param children 子节点数据
     */
    void setChildren(List<T> children);
    /**
     * 获取父节点
     * @author QiQiDream
     * @since 2019/5/15 10:29
     * @return java.util.List<T>
     */
    List<T> getChildren();
}
