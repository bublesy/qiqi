package com.qiqi.warehouse.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.endproductwarehouse.model.EndProductWarehouseDTO;
import com.qiqi.warehouse.entity.WarehouseDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiqi.warehouse.model.WarehouseDTO;

/**
 * <p>
 * 仓库 服务类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-18
 */
public interface WarehouseService extends IService<WarehouseDO> {

    /**
     * a
     * @param objectPage
     * @param warehouseDTO
     * @return
     */
    IPage<WarehouseDTO> wareList(Page<Object> objectPage, WarehouseDTO warehouseDTO);
}
