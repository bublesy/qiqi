package com.qiqi.admin.purchase.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiqi.admin.basicdata.model.SupplierCardboardQuotationVO;
import com.qiqi.admin.basicdata.model.SupplierVO;
import com.qiqi.admin.purchase.model.PurchaseOrderVO;
import com.qiqi.basicdata.entity.SupplierDO;
import com.qiqi.endproductwarehouse.entity.EndProductWarehouseDO;
import com.qiqi.endproductwarehouse.service.EndProductWarehouseService;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.service.OrderService;
import com.qiqi.purchase.entity.PurchaseOrderDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.purchase.model.PurchaseOrderDTO;
import com.qiqi.sys.entity.SysUserDO;
import com.qiqi.sys.service.SysUserService;
import com.qiqi.warehouse.entity.WarehouseDO;
import com.qiqi.warehouse.service.WarehouseService;
import io.swagger.annotations.*;
import org.springframework.security.core.parameters.P;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.purchase.service.PurchaseOrderService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 采购单 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api("采购单相关接口")
@RestController
@RequestMapping("/purchase/order")
public class PurchaseOrderController {

    @Resource
    private PurchaseOrderService purchaseOrderService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private WarehouseService warehouseService;
    @Resource
    private EndProductWarehouseService endProductWarehouseService;
    @Resource
    private OrderService orderService;


    @ApiOperation(value = "获取采购单(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<PurchaseOrderVO> getPurchaseOrderPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                            @RequestParam(value = "size",defaultValue = "10") Long size,
                                                            @RequestParam(value = "customerName") String customerName,
                                                            @RequestParam(value = "quantityOverdue") String quantityOverdue,
                                                            @RequestParam(value = "time") String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd MM:ss");
        String format = df.format(new Date());
        IPage<OrderDO> iPage = orderService.GetList(new Page<>(page,size),customerName,quantityOverdue,time,format);
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<PurchaseOrderVO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取采购单(单个)")
    @GetMapping("/{id}")
    public PurchaseOrderDO getPurchaseOrder(@PathVariable Long id) {
        //todo: 需要转Vo

        return purchaseOrderService.getById(id);
    }

    @ApiOperation(value = "获取名字(单个)")
    @GetMapping("/getNameById/{id}")
    public String getNameById(@PathVariable Long id) {
        OrderDO byId = orderService.getById(id);
        Long ids = byId.getCreatedBy();
        SysUserDO users = sysUserService.getById(ids);
        String nickname = users.getNickname();
        return nickname;
    }

    @ApiOperation(value = "获取名字(单个)")
    @GetMapping("/getNamesById/{id}")
    public String getNamesById(@PathVariable Long id) {
        SysUserDO users = sysUserService.getById(id);
        String nickname = users.getNickname();
        return nickname;
    }

    @ApiOperation(value = "入库(单个)")
    @PostMapping("/warehousing")
    public Boolean warehousing(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        boolean flag =false;
        OrderDO orderDO = new OrderDO();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd MM:ss");
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        PurchaseOrderDO purchaseOrderDO = new PurchaseOrderDO();

        String no = df.format(new Date());
        if (purchaseOrderDTO.getIsProduct().equals("成品")){
            EndProductWarehouseDO endProductWarehouseDO = new EndProductWarehouseDO();
            BeanUtil.copyProperties(purchaseOrderDTO,endProductWarehouseDO);
            endProductWarehouseDO.setWarehouseNo(no);
            endProductWarehouseDO.setWarehousingData(df2.format(new Date()));
            endProductWarehouseDO.setBillingData(df2.format(new Date()));
            endProductWarehouseDO.setCustomerId(purchaseOrderDTO.getCustomerName());
            endProductWarehouseDO.setSpecifications(purchaseOrderDTO.getStare());
            endProductWarehouseDO.setWidth(purchaseOrderDTO.getWidth());
            endProductWarehouseDO.setLength(purchaseOrderDTO.getLength());
            endProductWarehouseDO.setTypeNo(purchaseOrderDTO.getModelNo());
            endProductWarehouseDO.setPurQuantity(purchaseOrderDTO.getPurchaseQuantity());
            endProductWarehouseDO.setEndProductPos(purchaseOrderDTO.getPurchaseQuantity());
            endProductWarehouseDO.setHeight(purchaseOrderDTO.getHeight());
            endProductWarehouseDO.setCheckNum(endProductWarehouseDO.getEndProductPos());
            endProductWarehouseDO.setUnitPrice(purchaseOrderDTO.getCostPrice());
            endProductWarehouseDO.setOrderId(purchaseOrderDTO.getCustomerName());
            flag = endProductWarehouseService.saveOrUpdate(endProductWarehouseDO);
            BeanUtil.copyProperties(purchaseOrderDTO,orderDO);
            orderDO.setId(purchaseOrderDTO.getOrderId());
            orderDO.setWosState("入仓未出货");
            orderDO.setProductSpace(endProductWarehouseDO.getEndProductPos());
            orderService.updateById(orderDO);
            BeanUtil.copyProperties(purchaseOrderDTO,purchaseOrderDO);
            purchaseOrderDO.setEndProductPos(purchaseOrderDO.getPurchaseQuantity());
            purchaseOrderService.updateById(purchaseOrderDO);
        }else{
            WarehouseDO warehouseDO = new WarehouseDO();
            BeanUtil.copyProperties(purchaseOrderDTO,warehouseDO);
            warehouseDO.setWarehouseNo(no);
            warehouseDO.setWarehousingDate(df2.format(new Date()));
            warehouseDO.setBillingDate(df2.format(new Date()));
            warehouseDO.setSpecifications(purchaseOrderDTO.getStare());
            warehouseDO.setPaperLength(purchaseOrderDTO.getLength());
            warehouseDO.setCustomerId(purchaseOrderDTO.getCustomerName());
            warehouseDO.setPaperWidth(purchaseOrderDTO.getWidth());
            warehouseDO.setUnitPrice(purchaseOrderDTO.getCostPrice());
            warehouseDO.setPosition(purchaseOrderDTO.getPurchaseQuantity());
            warehouseDO.setCheckNum(warehouseDO.getPurchaseQuantity());
            warehouseDO.setOrderId(purchaseOrderDTO.getCustomerName());
            flag = warehouseService.saveOrUpdate(warehouseDO);
            BeanUtil.copyProperties(purchaseOrderDTO,orderDO);
            orderDO.setId(purchaseOrderDTO.getOrderId());
            orderDO.setWosState("字板已入仓");
            orderDO.setSpace(warehouseDO.getPosition());
            orderService.updateById(orderDO);
            BeanUtil.copyProperties(purchaseOrderDTO,purchaseOrderDO);
            purchaseOrderDO.setPosition(purchaseOrderDO.getPurchaseQuantity());
            purchaseOrderService.updateById(purchaseOrderDO);
        }
        orderDO.setId(purchaseOrderDTO.getOrderId());
        Integer a = Integer.parseInt(purchaseOrderDTO.getPurchaseQuantity());
        orderDO.setIncomeNum(a);
        orderService.updateById(orderDO);
        return flag;
    }


    @ApiOperation(value = "修改采购单")
    @PutMapping("")
    public Boolean updatePurchaseOrder(@RequestBody PurchaseOrderDO purchaseOrderDO) {
        return purchaseOrderService.updateById(purchaseOrderDO);
    }

    @ApiOperation(value = "新增采购单")
    @PostMapping("/add")
    public Boolean savePurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrderDO purchaseOrderDO = new PurchaseOrderDO();
        BeanUtil.copyProperties(purchaseOrderDTO,purchaseOrderDO);
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String no = df.format(new Date());
        purchaseOrderDO.setDocumentsNo(no);

        return purchaseOrderService.saveOrUpdate(purchaseOrderDO);
    }

    @ApiOperation(value = "删除采购单(批量))")
    @DeleteMapping("")
    public Boolean deletePurchaseOrderByIds(@RequestBody List<Long> idList) {
        return purchaseOrderService.removeByIds(idList);
    }

    @ApiOperation(value = "清空(单个))")
    @GetMapping("/remove/{id}")
    public Boolean deletePurchaseOrderById(@PathVariable Long id) {
        return purchaseOrderService.empty(id);
    }
}
