package com.qiqi.admin.endproductwarehouse.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.admin.warehouse.model.WarehouseList;
import com.qiqi.endproductwarehouse.entity.EndProductWarehouseDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.endproductwarehouse.model.EndProductWarehouseDTO;
import com.qiqi.order.entity.DeliveryNoteDO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.DeliveryNoteService;
import com.qiqi.order.service.OrderService;
import com.qiqi.warehouse.entity.WarehouseDO;
import com.qiqi.warehouse.service.WarehouseService;
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
import java.util.ArrayList;
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
    @Resource
    WarehouseService warehouseService;
    @Resource
    private DeliveryNoteService deliveryNoteService;

    @ApiOperation(value = "获取产品仓库(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<EndProductWarehouseDTO> getEndProductWarehousePage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                                        @RequestParam(value = "size",defaultValue = "10") Long size,
                                                                        @RequestParam(value = "carryTo",required = false) String carryTo,
                                                                        @RequestParam(value = "time",required = false) String time){
        //todo: 需要转Vo
        IPage<EndProductWarehouseDTO> iPage = endProductWarehouseService.getList(new Page<>(page,size),carryTo,time);
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<EndProductWarehouseDTO>>() {}, iPage.getRecords()));
    }

    @PostMapping("/endList")
    public PageEntity<EndProductWarehouseDTO> endList(@RequestBody EndProductWarehouseDTO endProductWarehouseDTO){
        IPage<EndProductWarehouseDTO> iPage = endProductWarehouseService.endList(new Page<>(endProductWarehouseDTO.getPage(),endProductWarehouseDTO.getSize()),endProductWarehouseDTO);
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<EndProductWarehouseDTO>>() {}, iPage.getRecords()));
    }

    @GetMapping("/noList")
    public List noList(){
        return endProductWarehouseService.list();
    }

    @ApiOperation(value = "盘点(批量))")
    @PostMapping("/check")
    public Boolean check(@RequestBody WarehouseList warehouseList){
        boolean flag = false;
        EndProductWarehouseDO endProductWarehouseDO = new EndProductWarehouseDO();
        List<Long> ids = warehouseList.getIds();
        List<String> surplusNums = warehouseList.getSurplusNums();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss");
        for(int i = 0;i<ids.size();i++){
            endProductWarehouseDO.setId(ids.get(i));
            endProductWarehouseDO.setEndProductPos(surplusNums.get(i));
            flag = endProductWarehouseService.updateById(endProductWarehouseDO);
        }
        return flag;
    }


    @PostMapping("/updatePosting")
    public Boolean updatePosting(@RequestBody List<Long> ids){
        boolean flag = false;
        EndProductWarehouseDO byId = null;
        EndProductWarehouseDO endProductWarehouseDO = new EndProductWarehouseDO();
        WarehouseDO warehouseDO = new WarehouseDO();
        for(Long id :ids){
            byId = endProductWarehouseService.getById(id);
            if (byId == null){
                WarehouseDO byId1 = warehouseService.getById(id);
                BeanUtil.copyProperties(byId1,warehouseDO);
                warehouseDO.setCarryTo("已过账");
                warehouseService.updateById(warehouseDO);
                flag = true;
            }else{
                BeanUtil.copyProperties(byId,endProductWarehouseDO);
                endProductWarehouseDO.setPosting("已过账");
                endProductWarehouseService.updateById(endProductWarehouseDO);
                flag = true;
            }
        }
        return flag;
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
        String orderId = endProductWarehouseDO.getOrderId();
        Long id = endProductWarehouseDO.getId();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String deliveryQuantity = endProductWarehouseDO.getDeliveryQuantity();
        int j = Integer.parseInt(deliveryQuantity);
        DeliveryNoteDO deliveryNoteDO = new DeliveryNoteDO();
        String endProductPos = endProductWarehouseDO.getEndProductPos();
        int i = Integer.parseInt(endProductPos);
        int g =i-j;
        EndProductWarehouseDO byId = endProductWarehouseService.getById(id);
        if (byId.getAlreadyDeliveryQuantity()!=null){
            endProductWarehouseDO.setAlreadyDeliveryQuantity(byId.getAlreadyDeliveryQuantity()+j);
        }else{
            endProductWarehouseDO.setAlreadyDeliveryQuantity(Integer.parseInt(endProductWarehouseDO.getDeliveryQuantity()));
        }
        if (deliveryQuantity!=null || deliveryQuantity !=""){
            endProductWarehouseDO.setEndProductPos(String.valueOf(g));
            endProductWarehouseDO.setCarryTo("已送货");
            endProductWarehouseDO.setId(id);
            endProductWarehouseDO.setOutNo(dateFormat.format(new Date()));
            endProductWarehouseDO.setOutDate(new Date());
        }
        endProductWarehouseService.saveOrUpdate(endProductWarehouseDO);
        deliveryNoteDO.setOrderId(Long.parseLong(orderId));
        deliveryNoteDO.setSendNum(j);
        deliveryNoteDO.setOutNo(dateFormat.format(new Date()));
        OrderDO orderDO = new OrderDO();
        orderDO.setId(Long.parseLong(orderId));
        orderDO.setSendNum(endProductWarehouseDO.getAlreadyDeliveryQuantity());
        orderDO.setWosState("已送货");
        orderDO.setShipDate(endProductWarehouseDO.getOutDate());
        orderDO.setOutNo(endProductWarehouseDO.getOutNo());
        orderService.updateById(orderDO);
        return deliveryNoteService.save(deliveryNoteDO);
    }


    @ApiOperation(value = "成品库存盘点")
    @PostMapping("/addCheck")
    public Boolean addCheck(@RequestBody EndProductWarehouseDO endProductWarehouseDO) {
        String checkNum = endProductWarehouseDO.getCheckNum();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss");
        if (checkNum!=null || checkNum !=""){
            endProductWarehouseDO.setCheckDate(df.format(new Date()));
            endProductWarehouseDO.setEndProductPos(checkNum);
        }
        return endProductWarehouseService.saveOrUpdate(endProductWarehouseDO);
    }

    @ApiOperation(value = "删除产品仓库(批量))")
    @DeleteMapping("")
    public Boolean deleteEndProductWarehouseByIds(@RequestBody List<Long> idList) {
        return endProductWarehouseService.removeByIds(idList);
    }

    @ApiOperation(value = "修改状态(批量))")
    @PostMapping("/updateState")
    public Boolean updateState(@RequestBody List<Long> idList) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        EndProductWarehouseDO byId = null;
        for (Long s:idList){
            byId = endProductWarehouseService.getById(s);
        }
        EndProductWarehouseDO endProductWarehouseDO = new EndProductWarehouseDO();
        String checkNum = byId.getEndProductPos();
        int check = Integer.parseInt(checkNum);
        String delivery = byId.getDeliveryQuantity();
        int del = Integer.parseInt(delivery);
        int i = check - del;
        String s = String.valueOf(i);
        endProductWarehouseDO.setEndProductPos(s);
        endProductWarehouseDO.setCheckDate(df.format(new Date()));
        endProductWarehouseDO.setId(byId.getId());
        endProductWarehouseDO.setCarryTo("已送货");
        endProductWarehouseDO.setOutNo(dateFormat.format(new Date()));
        endProductWarehouseDO.setOutDate(new Date());
        String deliver= byId.getDeliveryQuantity();
        Integer b = Integer.parseInt(deliver);
        if (byId.getAlreadyDeliveryQuantity() == null){
            endProductWarehouseDO.setAlreadyDeliveryQuantity(b);
        }else{
            Integer a = b+byId.getAlreadyDeliveryQuantity();
            endProductWarehouseDO.setAlreadyDeliveryQuantity(a);
        }
        String order = byId.getOrderId();
        String deliveryQuantity = byId.getDeliveryQuantity();
        Long orderId = Long.parseLong(order);
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        Integer a = Integer.parseInt(deliveryQuantity);
        orderDO.setSendNum(a);
        orderDO.setWosState("已送货");
        orderDO.setShipDate(endProductWarehouseDO.getOutDate());
        orderDO.setOutNo(endProductWarehouseDO.getOutNo());
        orderService.updateById(orderDO);
        return endProductWarehouseService.updateById(endProductWarehouseDO);
    }

    @ApiOperation(value = "删除产品仓库(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteEndProductWarehouseById(@PathVariable Long id) {
        return endProductWarehouseService.removeById(id);
    }

    @GetMapping("/getEndWarDate")
    public List getEndWarDate(){
       return endProductWarehouseService.list(new QueryWrapper<EndProductWarehouseDO>().le("end_product_pos", 200));
    }

    @GetMapping("/getEndDisData")
    public List getEndDisData(){
        List<EndProductWarehouseDO> list = endProductWarehouseService.list();
        List endList = new ArrayList();
        for (EndProductWarehouseDO endProductWarehouseDO :list){
            Integer alreadyDeliveryQuantity = endProductWarehouseDO.getAlreadyDeliveryQuantity();
            String orderQuantity = endProductWarehouseDO.getOrderQuantity();
            int parseInt = Integer.parseInt(orderQuantity);
            if (alreadyDeliveryQuantity!=parseInt){
                endList.add(endProductWarehouseDO);
            }
        }
        return endList;
    }

}
