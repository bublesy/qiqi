package com.qiqi.admin.endproductwarehouse.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiqi.endproductwarehouse.entity.EndProductWarehouseDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.endproductwarehouse.service.EndProductWarehouseService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 产品仓库 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Api("产品仓库相关接口")
@RestController
@RequestMapping("/end/product/warehouse")
public class EndProductWarehouseController {

    @Resource
    private EndProductWarehouseService endProductWarehouseService;
    @Resource
    private OrderService orderService;

    @ApiOperation(value = "获取产品仓库(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<EndProductWarehouseDO> getEndProductWarehousePage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                                        @RequestParam(value = "size",defaultValue = "10") Long size,
                                                                        @RequestParam(value = "carryTo") String carryTo,
                                                                        @RequestParam(value = "time") String time){
        //todo: 需要转Vo
        LambdaQueryWrapper<EndProductWarehouseDO> wrapper = new LambdaQueryWrapper<EndProductWarehouseDO>();
        wrapper.like(!ObjectUtils.isEmpty(time),EndProductWarehouseDO::getCreatedTime,time);
        if (carryTo.equals("已送货")){
            wrapper.le(!ObjectUtils.isEmpty(carryTo),EndProductWarehouseDO::getCarryTo,carryTo);
        }else{
            wrapper.ge(!ObjectUtils.isEmpty(carryTo),EndProductWarehouseDO::getCarryTo,carryTo);
        }
        IPage<EndProductWarehouseDO> iPage = endProductWarehouseService.page(new Page<>(page,size),wrapper);
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<EndProductWarehouseDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取产品仓库(单个)")
    @GetMapping("/{id}")
    public EndProductWarehouseDO getEndProductWarehouse(@PathVariable Long id) {
        //todo: 需要转Vo

        return endProductWarehouseService.getById(id);
    }

    @ApiOperation(value = "修改产品仓库")
    @PutMapping("")
    public Boolean updateEndProductWarehouse(@RequestBody EndProductWarehouseDO endProductWarehouseDO) {
        return endProductWarehouseService.updateById(endProductWarehouseDO);
    }

    @ApiOperation(value = "新增产品仓库")
    @PostMapping("/add")
    public Boolean saveEndProductWarehouse(@RequestBody EndProductWarehouseDO endProductWarehouseDO) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(new Date());
        endProductWarehouseDO.setCheckDate(format);
        return endProductWarehouseService.saveOrUpdate(endProductWarehouseDO);
    }

    @ApiOperation(value = "删除产品仓库(批量))")
    @DeleteMapping("")
    public Boolean deleteEndProductWarehouseByIds(@RequestBody List<Long> idList) {
        return endProductWarehouseService.removeByIds(idList);
    }

    @ApiOperation(value = "修改状态(批量))")
    @PostMapping("/updateState")
    public Boolean updateState(@RequestBody List<String> idList) {
        String join = StringUtils.join(idList, ",");
        Long id = Long.parseLong(join);
        EndProductWarehouseDO endProductWarehouseDO = new EndProductWarehouseDO();
        EndProductWarehouseDO byId = endProductWarehouseService.getById(join);
        String order = byId.getOrderId();
        String deliveryQuantity = byId.getDeliveryQuantity();
        Long orderId = Long.parseLong(order);
        endProductWarehouseDO.setId(id);
        endProductWarehouseDO.setCarryTo("已送货");
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        Integer a = Integer.parseInt(deliveryQuantity);
        orderDO.setSendNum(a);
        orderDO.setWosState("已送货");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        Date date = null;
        try {
            date = df.parse(byId.getDeliveryDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderDO.setDeliveryDate(date);
        orderDO.setOutNo(byId.getWarehouseNo());
        orderService.updateById(orderDO);
        return endProductWarehouseService.updateById(endProductWarehouseDO);
    }

    @ApiOperation(value = "删除产品仓库(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteEndProductWarehouseById(@PathVariable Long id) {
        return endProductWarehouseService.removeById(id);
    }
}
