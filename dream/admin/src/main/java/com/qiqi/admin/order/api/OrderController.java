package com.qiqi.admin.order.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.admin.order.util.IdGeneratorUtils;
import com.qiqi.admin.order.util.TimeAddEight;
import com.qiqi.basicdata.entity.CustomerInformationDO;
import com.qiqi.basicdata.entity.PaperboardDataSettingDO;
import com.qiqi.basicdata.entity.SupplierDO;
import com.qiqi.basicdata.service.CustomerInformationService;
import com.qiqi.basicdata.service.PaperboardDataSettingService;
import com.qiqi.basicdata.service.SupplierService;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.endproductwarehouse.entity.EndProductWarehouseDO;
import com.qiqi.endproductwarehouse.service.EndProductWarehouseService;
import com.qiqi.order.dto.OrderDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.entity.ScheduleDO;
import com.qiqi.order.service.OrderService;
import com.qiqi.order.service.ScheduleService;
import com.qiqi.sys.entity.SysUserDO;
import com.qiqi.sys.service.SysUserService;
import com.qiqi.warehouse.entity.WarehouseDO;
import com.qiqi.warehouse.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.IdGenerator;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import sun.awt.SunHints;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api(tags = "订单相关接口")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private CustomerInformationService customerInformationService;

    @Resource
    private PaperboardDataSettingService paperboardDataSettingService;

    @Resource
    private EndProductWarehouseService endProductWarehouseService;

    @Resource
    private SysUserService sysUserService;
    @Resource
    private WarehouseService warehouseService;

    private String state = "成品";

    @ApiOperation(value = "获取(列表)")
    @PostMapping("/list")
    public PageEntity<OrderDO> getOrderPage(@RequestBody OrderDTO query) {
        if(query.getOrderDate() != null){
            query.setOrderDate(TimeAddEight.formatTimeEight(query.getOrderDate()));
        }
        if(query.getDeliveryDate() != null){
            query.setDeliveryDate(TimeAddEight.formatTimeEight(query.getDeliveryDate()));
        }
        QueryWrapper<OrderDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getNo()),"no",query.getNo())
                .like(StringUtils.isNotBlank(query.getName()),"name",query.getName())
                .like(StringUtils.isNotBlank(query.getCustomerNo()),"customer_no",query.getCustomerNo())
                .eq(!ObjectUtils.isEmpty(query.getOrderDate()),"order_date",query.getOrderDate())
                .eq(!ObjectUtils.isEmpty(query.getDeliveryDate()),"delivery_date",query.getDeliveryDate())
                .eq(!ObjectUtils.isEmpty(query.getWosState()),"wos_state",query.getWosState())
                .eq(StringUtils.isNotBlank(query.getIsProduct()),"is_product",query.getIsProduct());
        IPage<OrderDO> iPage = orderService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<OrderDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取(单个)")
    @GetMapping("/{id}")
    public OrderDO getOrder(@PathVariable Long id) {
        //todo: 需要转Vo

        return orderService.getById(id);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Boolean updateOrder(@RequestBody OrderDO orderDO) {
        return orderService.updateById(orderDO);
    }

    @ApiOperation(value = "退货")
    @PostMapping("refund")
    public Boolean refund(@RequestBody OrderDO orderDO){
        if(orderDO == null){
            return false;
        }
        if (orderDO.getIsProduct().equals("成品")){
            EndProductWarehouseDO endProductWarehouseDO = endProductWarehouseService.getOne(new QueryWrapper<EndProductWarehouseDO>().eq("order_id",orderDO.getId()));
            if(endProductWarehouseDO != null && endProductWarehouseDO.getEndProductPos() != null && orderDO.getRefundNum() != null){
                Integer num = Integer.parseInt(endProductWarehouseDO.getEndProductPos()) + Integer.parseInt(orderDO.getRefundNum());
                endProductWarehouseDO.setEndProductPos(num.toString());
                boolean b = endProductWarehouseService.updateById(endProductWarehouseDO);
                if(b){
                    orderDO.setRefundTime(new Date());
                    OrderDO orderDO1 = orderService.getById(orderDO.getId());
                    Integer refundNum1 = (orderDO1.getRefundNum() == null ? 0 : Integer.parseInt(orderDO1.getRefundNum()));
                    Integer refundNum = Integer.parseInt(orderDO.getRefundNum()) + refundNum1;
                    orderDO.setRefundNum(refundNum.toString());
                }

            }else {
                orderDO.setRefundNum(null);
                orderDO.setRefundTime(null);
                return false;
            }
        }else{
            WarehouseDO warehouseDO = warehouseService.getOne(new QueryWrapper<WarehouseDO>().eq("order_id",orderDO.getId()));
            if(warehouseDO != null && warehouseDO.getPosition() != null && orderDO.getRefundNum() != null){
                Integer num = Integer.parseInt(warehouseDO.getPosition()) + Integer.parseInt(orderDO.getRefundNum());
                warehouseDO.setPosition(num.toString());
                boolean b = warehouseService.updateById(warehouseDO);
                if(b){
                    orderDO.setRefundTime(new Date());
                    OrderDO orderDO1 = orderService.getById(orderDO.getId());
                    Integer refundNum1 = (orderDO1.getRefundNum() == null ? 0 : Integer.parseInt(orderDO1.getRefundNum()));
                    Integer refundNum = Integer.parseInt(orderDO.getRefundNum()) + refundNum1;
                    orderDO.setRefundNum(refundNum.toString());
                }

            }else {
                orderDO.setRefundNum(null);
                orderDO.setRefundTime(null);
                return false;
            }
        }

        return orderService.updateById(orderDO);
    }

    @ApiOperation(value = "新增or修改")
    @PostMapping("")
    public Boolean saveOrder(@RequestBody OrderDO orderDO) {
        if(orderDO == null){
            return false;
        }
        if(orderDO.getId() == null){
            IdGeneratorUtils idGeneratorUtils = new IdGeneratorUtils();
            String no = idGeneratorUtils.nextId();
            orderDO.setNo(no);
            orderDO.setOrderDate(new Date());
        }
        if(orderDO.getDeliveryDate() != null && orderDO.getModCount() > 0){
            orderDO.setDeliveryDate(TimeAddEight.formatTimeEight(orderDO.getDeliveryDate()));
        }
        ScheduleDO scheduleDO = new ScheduleDO();
        if(orderDO.getId() == null && !state.equals(orderDO.getIsProduct())){
            BeanUtils.copyProperties(orderDO,scheduleDO);
            scheduleDO.setDate(orderDO.getDeliveryDate());
            scheduleService.save(scheduleDO);
        }
        if(orderDO.getId() != null && !state.equals(orderDO.getIsProduct())){
                BeanUtils.copyProperties(orderDO,scheduleDO);
                scheduleDO.setDate(orderDO.getDeliveryDate());
                scheduleDO.setId(orderDO.getScheduleId());
                scheduleService.saveOrUpdate(scheduleDO);
        }
        if(orderDO.getId() != null && state.equals(orderDO.getIsProduct())){
            if(orderDO.getScheduleId() != null){
                scheduleService.delete(orderDO.getScheduleId());
            }
        }
        if(scheduleDO != null){
            orderDO.setScheduleId(scheduleDO.getId());
        }
        if(orderDO.getId() == null){
            orderDO.setWosState("新订单");
        }
        return orderService.saveOrUpdate(orderDO);
    }

    @ApiOperation(value = "删除(批量))")
    @DeleteMapping("")
    public Boolean deleteOrderByIds(@RequestBody List<Long> idList) {
        return orderService.removeByIds(idList);
    }

    @ApiOperation(value = "删除(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteOrderById(@PathVariable Long id) {
        OrderDO order = orderService.getById(id);
        scheduleService.removeById(order.getId());
        return orderService.removeById(id);
    }

    @GetMapping("/supplier")
    public List<SupplierDO> getSupplier(){
        return supplierService.list();
    }

    @GetMapping("/customer")
    public List<CustomerInformationDO> getList(){
        return customerInformationService.list();
    }

    @GetMapping("/stare")
    public List<PaperboardDataSettingDO> getstare(){
        return paperboardDataSettingService.list();
    }

    @GetMapping("/user/{id}")
    public SysUserDO getUser(@PathVariable Long id){
        return sysUserService.getById(id);
    }
}
