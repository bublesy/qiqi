package com.qiqi.common.util.tree;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树工具
 *
 * 使用方法：data是生成 树 的 数据源, 实体类要实现DataTree
 *       List<实体类> data;
 *       List<实体类> tree = TreeUtil.getTreeList(0L, data);
 *
 * @author QiQiDream
 * @since 2019/1/11 16:03
 */
@Component
public class TreeUtil {
    /**
     * 获取顶层节点
     * @param topId 顶点 一般是0L
     * @param entityList 数据
     * @return list
     */
    public static <T extends DataTree<T>> List<T> getTreeList(Long topId, List<T> entityList){
        //存储顶层的数据
        List<T> resultList = new ArrayList<>();

        Map<Object, T> treeMap = new HashMap<>(16);
        T itemTree;

        for(int i=0;i<entityList.size()&&!entityList.isEmpty();i++) {
            itemTree = entityList.get(i);
            //把所有的数据放到map当中，id为key
            treeMap.put(itemTree.getId().toString(),itemTree);
            //把顶层数据放到集合中
            if(topId.equals(itemTree.getParentId())) {
                resultList.add(itemTree);
            }
        }

        //循环数据，把数据放到上一级的children属性中
        for(int i = 0; i< entityList.size()&&!entityList.isEmpty();i++) {
            itemTree = entityList.get(i);
            //在map集合中寻找父亲
            T data = treeMap.get(itemTree.getParentId().toString());
            //判断父亲有没有
            if(data != null) {
                if(data.getChildren() == null) {
                    data.setChildren(new ArrayList<>());
                }
                //把子节点 放到父节点childList当中
                data.getChildren().add(itemTree);
                //把放好的数据放回map当中
                treeMap.put(itemTree.getParentId().toString(), data);
            }
        }
        return resultList;
    }

}
