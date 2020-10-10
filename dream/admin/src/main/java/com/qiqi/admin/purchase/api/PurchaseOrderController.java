package com.qiqi.admin.purchase.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiqi.admin.purchase.model.PurchaseOrderVO;
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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.purchase.service.PurchaseOrderService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;

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

    @GetMapping("/purList")
    public PageEntity<PurchaseOrderDO> getPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                            @RequestParam(value = "size",defaultValue = "10") Long size,
                                                            @RequestParam(value = "time",required = false) String time) {
        LambdaQueryWrapper<PurchaseOrderDO> wrapper = new LambdaQueryWrapper<PurchaseOrderDO>();
        wrapper.like(!ObjectUtils.isEmpty(time),PurchaseOrderDO::getCreditDate,time);
        IPage<PurchaseOrderDO> iPage = purchaseOrderService.page(new Page<>(page,size),wrapper);
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<PurchaseOrderDO>>() {}, iPage.getRecords()));
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
            if ( purchaseOrderDTO.getReturnNum() == null ||  purchaseOrderDTO.getReturnNum() == ""){
                endProductWarehouseDO.setEndProductPos(purchaseOrderDTO.getPurchaseQuantity());
                endProductWarehouseDO.setStorageQuantity(Integer.parseInt(purchaseOrderDTO.getPurchaseQuantity()) );
            }else{
                String purchaseQuantity = purchaseOrderDTO.getPurchaseQuantity();
                String returnNum = purchaseOrderDTO.getReturnNum();
                int i = Integer.parseInt(purchaseQuantity);
                int j = Integer.parseInt(returnNum);
                int g = i-j;
                String a =String.valueOf(g);
                endProductWarehouseDO.setEndProductPos(a);
                endProductWarehouseDO.setStorageQuantity(g);
            }
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
            purchaseOrderDO.setEndProductPos(endProductWarehouseDO.getEndProductPos());
            purchaseOrderDO.setInProductDate(df2.format(new Date()));
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
            if ( purchaseOrderDTO.getReturnNum() == null ||  purchaseOrderDTO.getReturnNum() == ""){
                warehouseDO.setPosition(purchaseOrderDTO.getPurchaseQuantity());
            }else{
                String dtoPurchaseQuantity = purchaseOrderDTO.getPurchaseQuantity();
                String returnNum = purchaseOrderDTO.getReturnNum();
                int i = Integer.parseInt(dtoPurchaseQuantity);
                int j = Integer.parseInt(returnNum);
                int g = i-j;
                String a =String.valueOf(g);
                purchaseOrderDO.setEndProductPos(a);
            }
            warehouseDO.setCheckNum(warehouseDO.getPurchaseQuantity());
            warehouseDO.setOrderId(purchaseOrderDTO.getCustomerName());
            flag = warehouseService.saveOrUpdate(warehouseDO);
            BeanUtil.copyProperties(purchaseOrderDTO,orderDO);
            orderDO.setId(purchaseOrderDTO.getOrderId());
            orderDO.setWosState("字板已入仓");
            orderDO.setSpace(warehouseDO.getPosition());
            orderService.updateById(orderDO);
            BeanUtil.copyProperties(purchaseOrderDTO,purchaseOrderDO);
            purchaseOrderDO.setPosition(warehouseDO.getPosition());
            purchaseOrderDO.setInProductDate(df2.format(new Date()));
            purchaseOrderService.updateById(purchaseOrderDO);
        }
        orderDO.setId(purchaseOrderDTO.getOrderId());
        Integer a = Integer.parseInt(purchaseOrderDTO.getPurchaseQuantity());
        orderDO.setIncomeNum(a);
        orderService.updateById(orderDO);
        return flag;
    }


    @ApiOperation(value = "修改采购单")
    @PutMapping("/update")
    public Boolean updatePurchaseOrder(@RequestBody PurchaseOrderDO purchaseOrderDO) {
        boolean flag = false;
        String carryTo = purchaseOrderDO.getCarryTo();
        Integer alreadyMoney = purchaseOrderDO.getAlreadyMoney();
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String settlementStatus = purchaseOrderDO.getSettlementStatus();
        Long id = purchaseOrderDO.getId();
        PurchaseOrderDO byId = purchaseOrderService.getById(id);
        Integer totalAmount = byId.getTotalAmount();
        Integer totalAmount1 = purchaseOrderDO.getTotalAmount();
        if (carryTo!=null || carryTo !=""){
            flag = purchaseOrderService.updateById(purchaseOrderDO);
        }
        if(alreadyMoney !=null && settlementStatus != null  ){
            String settlementDate = byId.getSettlementDate();
            if (byId.getAlreadyMoney() == null ){
                purchaseOrderDO.setAlreadyMoney(purchaseOrderDO.getAlreadyMoney());
            }else{
                purchaseOrderDO.setAlreadyMoney(byId.getAlreadyMoney()+purchaseOrderDO.getAlreadyMoney());
            }
            if (settlementDate!=null){
                purchaseOrderDO.setSettlementDate(settlementDate+"/"+df.format(new Date()));    
            }else{
                purchaseOrderDO.setSettlementDate(df.format(new Date()));
            }
            flag = purchaseOrderService.updateById(purchaseOrderDO);
        }
        if (totalAmount != totalAmount1){
            flag = purchaseOrderService.updateById(purchaseOrderDO);
        }

        return flag;
    }

    @ApiOperation(value = "新增采购单")
    @PostMapping("/add")
    public Boolean savePurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrderDO purchaseOrderDO = new PurchaseOrderDO();
        BeanUtil.copyProperties(purchaseOrderDTO,purchaseOrderDO);
        purchaseOrderDO.setOrderQuantity(purchaseOrderDTO.getOrderNum());
        purchaseOrderDO.setRidgeType(purchaseOrderDTO.getStare());
        purchaseOrderDO.setParPreSpe(purchaseOrderDTO.getPressureSpecification());
        purchaseOrderDO.setPaperHeight(purchaseOrderDTO.getHeight());
        purchaseOrderDO.setOrderId(purchaseOrderDTO.getId());
        String amount = purchaseOrderDO.getCostPrice();
        int i2 = Integer.parseInt(amount);
        String purchaseQuantity = purchaseOrderDO.getPurchaseQuantity();
        int i3 = Integer.parseInt(purchaseQuantity);
        int i1 =  i2*i3;
        purchaseOrderDO.setTotalAmount(i1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(purchaseOrderDO.getBillingDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String settlementPeriod = purchaseOrderDO.getSettlementPeriod();
        int i = Integer.parseInt(settlementPeriod);
        Long time = date.getTime() + 3600 * 1000 * 24 * i;
        date = new Date(time);
        purchaseOrderDO.setCreditDate(date);
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String no = df.format(new Date());
        purchaseOrderDO.setDocumentsNo(no);
        return purchaseOrderService.saveOrUpdate(purchaseOrderDO);
    }

    @ApiOperation(value = "新增退货单")
    @PostMapping("/returnAdd")
    public Boolean returnAdd(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrderDO purchaseOrderDO = new PurchaseOrderDO();
        BeanUtil.copyProperties(purchaseOrderDTO,purchaseOrderDO);
        PurchaseOrderDO purOrder = purchaseOrderService.getById(purchaseOrderDTO.getId());
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd MM:ss");
        if (!purchaseOrderDTO.getPosition().equals("0") ){
            WarehouseDO byId = warehouseService.getById(purchaseOrderDTO.getId());
            WarehouseDO warehouseDO = new WarehouseDO();
            BeanUtil.copyProperties(purchaseOrderDTO,warehouseDO);
            int pur = Integer.parseInt(byId.getPosition());
            int retNum = Integer.parseInt(purchaseOrderDTO.getReturnNum());
            int i = pur - retNum;
            String str = String.valueOf(i);
            warehouseDO.setPosition(str);
            purchaseOrderDO.setPosition(warehouseDO.getPosition());
            warehouseService.updateById(warehouseDO);
        }else if (!purchaseOrderDTO.getEndProductPos().equals("0")){
            EndProductWarehouseDO byId = endProductWarehouseService.getById(purchaseOrderDTO.getId());
            EndProductWarehouseDO endProductWarehouseDO = new EndProductWarehouseDO();
            BeanUtil.copyProperties(purchaseOrderDTO,endProductWarehouseDO);
            int pur = Integer.parseInt(byId.getEndProductPos());
            int retNum = Integer.parseInt(purchaseOrderDTO.getReturnNum());
            int i = pur - retNum;
            String str = String.valueOf(i);
            endProductWarehouseDO.setEndProductPos(str);
            purchaseOrderDO.setEndProductPos(endProductWarehouseDO.getEndProductPos());
            endProductWarehouseService.updateById(endProductWarehouseDO);
        }
            purchaseOrderDO.setReturnDate(df2.format(new Date()));
         if ( purchaseOrderDTO.getReturnNum() == null ||  purchaseOrderDTO.getReturnNum() == ""){
             purchaseOrderDO.setReturnNum("0");
         }else if(purOrder.getReturnNum() == null || purOrder.getReturnNum() == ""){
             purchaseOrderDO.setReturnNum(purchaseOrderDTO.getReturnNum());
         }else{
             String returnNum = purOrder.getReturnNum();
             int i = Integer.parseInt(returnNum);
             String retNum = purchaseOrderDTO.getReturnNum();
             int o = Integer.parseInt(retNum);
             int p = i+o;
             String a = String.valueOf(p);
             purchaseOrderDO.setReturnNum(a);
         }
            return purchaseOrderService.updateById(purchaseOrderDO);
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

    @GetMapping("/getSupplierSettData")
    public List getSupplierSettData(){
        List<PurchaseOrderDO> list = purchaseOrderService.list();
        List supplierList = new ArrayList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (PurchaseOrderDO purchaseOrderDO:list){
            String settlementPeriod = purchaseOrderDO.getSettlementPeriod();
            int parseInt = Integer.parseInt(settlementPeriod);
            String billingDate = purchaseOrderDO.getBillingDate();
            Date dt = null;
            try {
                 dt= format.parse(billingDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.DAY_OF_YEAR,(parseInt+3));//日期加10天
            Date dt1=rightNow.getTime();
            long time1 = dt1.getTime();
            Date date = null;
            String format1 = format.format(new Date());
            try {
                date = format.parse(format1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long time = date.getTime();
            if (time1<=time){
                supplierList.add(purchaseOrderDO);
            }
        }
        return supplierList;
    }
}
