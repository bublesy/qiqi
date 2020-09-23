package com.qiqi.endproductwarehouse.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.endproductwarehouse.entity.EndProductWarehouseDO;
import com.qiqi.endproductwarehouse.mapper.EndProductWarehouseMapper;
import com.qiqi.endproductwarehouse.model.EndProductWarehouseDTO;
import com.qiqi.endproductwarehouse.service.EndProductWarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 产品仓库 服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Service
public class EndProductWarehouseServiceImpl extends ServiceImpl<EndProductWarehouseMapper, EndProductWarehouseDO> implements EndProductWarehouseService {
    @Resource
    EndProductWarehouseMapper endProductWarehouseMapper;
    @Override
    public IPage<EndProductWarehouseDTO> endList(Page<Object> objectPage, EndProductWarehouseDTO endProductWarehouseDTO) {
        return endProductWarehouseMapper.endList(objectPage,endProductWarehouseDTO);
    }
}
