package com.qiqi.material.mapper;

import com.qiqi.material.entity.MaterialInventoryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 辅料库存 Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-20
 */
public interface MaterialInventoryMapper extends BaseMapper<MaterialInventoryDO> {

    /**
     * a
     * @param specificationId
     * @return
     */
    MaterialInventoryDO getNumBySpecificationId(String specificationId);
}
