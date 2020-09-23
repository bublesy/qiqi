package com.qiqi.endproductwarehouse.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.endproductwarehouse.entity.EndProductWarehouseDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiqi.endproductwarehouse.model.EndProductWarehouseDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 产品仓库 Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
public interface EndProductWarehouseMapper extends BaseMapper<EndProductWarehouseDO> {

    /**
     * 查询
     * @param objectPage
     * @param endProductWarehouseDTO
     * @return
     */
    IPage<EndProductWarehouseDTO> endList(@Param("objectPage") Page<Object> objectPage,@Param("endProductWarehouseDTO") EndProductWarehouseDTO endProductWarehouseDTO);
}
