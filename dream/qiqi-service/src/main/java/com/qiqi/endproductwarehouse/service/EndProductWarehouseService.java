package com.qiqi.endproductwarehouse.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.endproductwarehouse.entity.EndProductWarehouseDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiqi.endproductwarehouse.model.EndProductWarehouseDTO;

/**
 * <p>
 * 产品仓库 服务类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
public interface EndProductWarehouseService extends IService<EndProductWarehouseDO> {

    /**
     * 查询
     * @param objectPage
     * @param endProductWarehouseDTO
     * @return
     */
    IPage<EndProductWarehouseDTO> endList(Page<Object> objectPage, EndProductWarehouseDTO endProductWarehouseDTO);

    /**
     * 查询
     * @param objectPage
     * @param carryTo
     * @param time
     * @return
     */
    IPage<EndProductWarehouseDTO> getList(Page<Object> objectPage, String carryTo, String time);
}
