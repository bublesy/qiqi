package com.qiqi.material.service;

import com.qiqi.material.entity.MaterialInventoryDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 辅料库存 服务类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-20
 */
public interface MaterialInventoryService extends IService<MaterialInventoryDO> {

    /**
     * a
     * @param specificationId
     * @return
     */
    MaterialInventoryDO getNumBySpecificationId(String specificationId);
}
