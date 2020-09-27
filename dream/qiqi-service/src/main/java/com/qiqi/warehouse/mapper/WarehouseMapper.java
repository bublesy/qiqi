package com.qiqi.warehouse.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.warehouse.entity.WarehouseDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiqi.warehouse.model.WarehouseDTO;

/**
 * <p>
 * 仓库 Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-18
 */
public interface WarehouseMapper extends BaseMapper<WarehouseDO> {

    /**
     * 列表
     * @param objectPage
     * @param warehouseDTO
     * @return
     */
    IPage<WarehouseDTO> wareList(Page<Object> objectPage, WarehouseDTO warehouseDTO);
}
