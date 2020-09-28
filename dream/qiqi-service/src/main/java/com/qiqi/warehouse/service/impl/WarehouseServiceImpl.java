package com.qiqi.warehouse.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.warehouse.entity.WarehouseDO;
import com.qiqi.warehouse.mapper.WarehouseMapper;
import com.qiqi.warehouse.model.WarehouseDTO;
import com.qiqi.warehouse.service.WarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 仓库 服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-18
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, WarehouseDO> implements WarehouseService {
    @Resource
    WarehouseMapper warehouseMapper;

    @Override
    public IPage<WarehouseDTO> wareList(Page<Object> objectPage, WarehouseDTO warehouseDTO) {
        return warehouseMapper.wareList(objectPage,warehouseDTO);
    }
}
