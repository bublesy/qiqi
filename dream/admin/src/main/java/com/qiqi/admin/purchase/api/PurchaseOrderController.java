package com.qiqi.admin.purchase.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiqi.admin.basicdata.model.SupplierCardboardQuotationVO;
import com.qiqi.admin.basicdata.model.SupplierVO;
import com.qiqi.admin.purchase.model.PurchaseOrderVO;
import com.qiqi.basicdata.entity.SupplierDO;
import com.qiqi.purchase.entity.PurchaseOrderDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
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
        //todo: 需要转Vo
        LambdaQueryWrapper<PurchaseOrderDO> wrapper = new LambdaQueryWrapper<PurchaseOrderDO>();
        wrapper.like(!ObjectUtils.isEmpty(customerName),PurchaseOrderDO::getCustomerName,customerName);
        wrapper.like(!ObjectUtils.isEmpty(time),PurchaseOrderDO::getCreatedTime,time);
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        if (quantityOverdue.equals("已过期")){
            wrapper.le(!ObjectUtils.isEmpty(quantityOverdue),PurchaseOrderDO::getDeliveryDate,date);
        }else{
            wrapper.ge(!ObjectUtils.isEmpty(quantityOverdue),PurchaseOrderDO::getDeliveryDate,date);
        }
        IPage<PurchaseOrderDO> iPage = purchaseOrderService.page(new Page<>(page,size),wrapper);
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<PurchaseOrderVO>>() {}, iPage.getRecords()));
    }

    @PostMapping("/listByIds")
    public PageEntity<PurchaseOrderVO> listByIds(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                 @RequestParam(value = "size",defaultValue = "10") Long size,
                                                 @RequestParam(value = "ids") List<Long> ids) {
        //todo: 需要转Vo
        IPage iPage = purchaseOrderService.SuppAndPurList(new Page<>(page,size),ids);
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
        PurchaseOrderDO byId = purchaseOrderService.getById(id);
        Long ids = byId.getCreatedBy();
        SysUserDO users = sysUserService.getById(ids);
        String nickname = users.getNickname();
        return nickname;
    }


    @ApiOperation(value = "修改采购单")
    @PutMapping("")
    public Boolean updatePurchaseOrder(@RequestBody PurchaseOrderDO purchaseOrderDO) {
        return purchaseOrderService.updateById(purchaseOrderDO);
    }

    @ApiOperation(value = "新增采购单")
    @PostMapping("/add")
    public Boolean savePurchaseOrder(@RequestBody PurchaseOrderDO purchaseOrderDO) {
        WarehouseDO warehouseDO = new WarehouseDO();
        BeanUtil.copyProperties(purchaseOrderDO,warehouseDO);
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmssms");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String no = df.format(new Date());
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd MM:ss");
        purchaseOrderDO.setDocumentsNo(no);
        warehouseDO.setWarehouseNo(purchaseOrderDO.getDocumentsNo());
        warehouseDO.setWarehousingDate(df2.format(new Date()));
        warehouseDO.setDrawerId(purchaseOrderDO.getCreatedBy());
        warehouseService.saveOrUpdate(warehouseDO);
        return purchaseOrderService.saveOrUpdate(purchaseOrderDO);
    }

    @ApiOperation(value = "删除采购单(批量))")
    @DeleteMapping("")
    public Boolean deletePurchaseOrderByIds(@RequestBody List<Long> idList) {
        return purchaseOrderService.removeByIds(idList);
    }

    @ApiOperation(value = "删除采购单(单个))")
    @DeleteMapping("/{id}")
    public Boolean deletePurchaseOrderById(@PathVariable Long id) {
        return purchaseOrderService.removeById(id);
    }
}
