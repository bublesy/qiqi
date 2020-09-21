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
import com.qiqi.order.dto.OrderDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.entity.ScheduleDO;
import com.qiqi.order.service.OrderService;
import com.qiqi.order.service.ScheduleService;
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
@Api("相关接口")
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

    private String state = "成品";
    private String state2 = "非成品";

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

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Boolean saveOrder(@RequestBody OrderDO orderDO) {
        IdGeneratorUtils idGeneratorUtils = new IdGeneratorUtils();
        String no = idGeneratorUtils.nextId();
        orderDO.setNo(no);
        ScheduleDO scheduleDO = new ScheduleDO();
        if(orderDO.getId() == null && !state.equals(orderDO.getIsProduct())){
            BeanUtils.copyProperties(orderDO,scheduleDO);
            scheduleDO.setDate(orderDO.getDeliveryDate());
            scheduleService.save(scheduleDO);
        }
        if(orderDO.getId() != null && !state.equals(orderDO.getIsProduct())){
            OrderDO order = orderService.getById(orderDO.getId());
                BeanUtils.copyProperties(orderDO,scheduleDO);
                scheduleDO.setDate(orderDO.getDeliveryDate());
                scheduleDO.setId(order.getScheduleId());
                scheduleService.saveOrUpdate(scheduleDO);
        }
        if(orderDO.getId() != null && state.equals(orderDO.getIsProduct())){
            OrderDO order = orderService.getById(orderDO.getId());
            if(order.getScheduleId() != null){
                scheduleService.delete(order.getScheduleId());
            }
        }

        orderDO.setOrderDate(new Date());
        if(orderDO.getDeliveryDate() != null){
            orderDO.setDeliveryDate(TimeAddEight.formatTimeEight(orderDO.getDeliveryDate()));
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
}
