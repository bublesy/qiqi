package com.qiqi.admin.warehouse.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.admin.warehouse.model.WarehouseVO;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.endproductwarehouse.entity.EndProductWarehouseDO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import com.qiqi.purchase.entity.PurchaseOrderDO;
import com.qiqi.warehouse.entity.WarehouseDO;
import com.qiqi.warehouse.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 仓库 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-18
 */
@Api("仓库相关接口")
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Resource
    private WarehouseService warehouseService;
    @Resource
    private OrderService orderService;

    @ApiOperation(value = "获取仓库(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<WarehouseVO> getWarehousePage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                    @RequestParam(value = "size",defaultValue = "10") Long size,
                                                    @RequestParam(value = "time") String time  ) {
        //todo: 需要转Vo
        LambdaQueryWrapper<WarehouseDO> wrapper = new LambdaQueryWrapper<WarehouseDO>();
        wrapper.like(!ObjectUtils.isEmpty(time),WarehouseDO::getCreatedTime,time);
        IPage<WarehouseDO> iPage = warehouseService.page(new Page<>(page,size),wrapper);
        //todo: 需要转Vo
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<WarehouseVO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取仓库(单个)")
    @GetMapping("/{id}")
    public WarehouseDO getWarehouse(@PathVariable Long id) {
        //todo: 需要转Vo

        return warehouseService.getById(id);
    }

    @ApiOperation(value = "修改仓库")
    @PutMapping("")
    public Boolean updateWarehouse(@RequestBody WarehouseDO warehouseDO) {
        return warehouseService.updateById(warehouseDO);
    }

    @ApiOperation(value = "新增仓库")
    @PostMapping("/add")
    public Boolean saveWarehouse(@RequestBody WarehouseDO warehouseDO) {
        return warehouseService.saveOrUpdate(warehouseDO);
    }

    @ApiOperation(value = "删除仓库(批量))")
    @DeleteMapping("")
    public Boolean deleteWarehouseByIds(@RequestBody List<Long> idList) {
        return warehouseService.removeByIds(idList);
    }

    @ApiOperation(value = "删除仓库(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteWarehouseById(@PathVariable Long id) {
        return warehouseService.removeById(id);
    }


    @ApiOperation(value = "修改状态(批量))")
    @PostMapping("/upState")
    public Boolean updateState(@RequestBody List<String> idList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss");
        String join = StringUtils.join(idList, ",");
        Long id = Long.parseLong(join);
        WarehouseDO warehouseDO = new WarehouseDO();
        WarehouseDO byId = warehouseService.getById(join);
        String checkNum = byId.getPosition();
        int check = Integer.parseInt(checkNum);
        String delivery = byId.getDeliveryQuantity();
        int del = Integer.parseInt(delivery);
        int i = check - del;
        String s = String.valueOf(i);
        warehouseDO.setPosition(s);
        warehouseDO.setCheckDate(df.format(new Date()));
        warehouseDO.setId(id);
        warehouseDO.setDeliveryStatus("已送货");
        warehouseDO.setOutNo(dateFormat.format(new Date()));
        warehouseDO.setOutDate(new Date());
        String order = byId.getOrderId();
        String deliveryQuantity = byId.getDeliveryQuantity();
        Long orderId = Long.parseLong(order);
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        Integer a = Integer.parseInt(deliveryQuantity);
        orderDO.setSendNum(a);
        orderDO.setWosState("已送货");
        orderDO.setShipDate(warehouseDO.getOutDate());
        orderDO.setOutNo(byId.getWarehouseNo());
        orderService.updateById(orderDO);

        return warehouseService.updateById(warehouseDO);
    }
}
