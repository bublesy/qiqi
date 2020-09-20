package com.qiqi.material.service.impl;

import com.qiqi.material.entity.MaterialInventoryDO;
import com.qiqi.material.mapper.MaterialInventoryMapper;
import com.qiqi.material.service.MaterialInventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 辅料库存 服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-20
 */
@Service
public class MaterialInventoryServiceImpl extends ServiceImpl<MaterialInventoryMapper, MaterialInventoryDO> implements MaterialInventoryService {
    @Resource
    MaterialInventoryMapper materialInventoryMapper;
    @Override
    public MaterialInventoryDO getNumBySpecificationId(String specificationId) {
        return materialInventoryMapper.getNumBySpecificationId(specificationId);
    }
}
