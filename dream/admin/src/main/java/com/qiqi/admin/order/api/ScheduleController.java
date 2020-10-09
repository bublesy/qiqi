package com.qiqi.admin.order.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.admin.order.util.TimeAddEight;
import com.qiqi.endproductwarehouse.entity.EndProductWarehouseDO;
import com.qiqi.endproductwarehouse.service.EndProductWarehouseService;
import com.qiqi.order.dto.ScheduleDTO;
import com.qiqi.order.entity.OrderDO;
import com.qiqi.order.entity.ScheduleDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.service.OrderService;
import com.qiqi.sys.entity.SysUserDO;
import com.qiqi.sys.service.SysUserService;
import com.qiqi.warehouse.entity.WarehouseDO;
import com.qiqi.warehouse.service.WarehouseService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.order.service.ScheduleService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 排期表 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api("排期相关接口")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    private Long scheduleId;

    private Integer product;

    private String finished;

    private Date date;

    protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");

    private Integer lossNum;

    @Resource
    private OrderService orderService;

    @Resource
    private EndProductWarehouseService endProductWarehouseService;

    @Resource
    private WarehouseService warehouseService;

    @ApiOperation(value = "获取排期(列表)")
    @PostMapping("/list")
    public PageEntity<ScheduleDO> getSchedulePage(@RequestBody ScheduleDTO query) {
        QueryWrapper<ScheduleDO> queryWrapper = new QueryWrapper<>();
        if (query.getDate() != null) {
            query.setDate(TimeAddEight.formatTimeEight(query.getDate()));
        }
        queryWrapper.eq(!ObjectUtils.isEmpty(query.getDate()), "date", query.getDate())
                .like(StringUtils.isNotBlank(query.getNo()), "no", query.getNo())
                .like(StringUtils.isNotBlank(query.getName()), "name", query.getName());
        IPage<ScheduleDO> iPage = scheduleService.page(new Page<>(query.getPage(), query.getCount()), queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(), Convert.convert(new TypeReference<List<ScheduleDO>>() {
        }, iPage.getRecords()));
    }

    @ApiOperation(value = "获取排期(单个)")
    @GetMapping("/{id}")
    public ScheduleDO getSchedule(@PathVariable Long id) {
        //todo: 需要转Vo

        return scheduleService.getById(id);
    }

    @ApiOperation(value = "修改排期")
    @PutMapping("")
    public Boolean updateSchedule(@RequestBody ScheduleDO scheduleDO) {
        return scheduleService.updateById(scheduleDO);
    }

    @ApiOperation(value = "新增排期")
    @PostMapping("")
    public Boolean saveSchedule(@RequestBody ScheduleDO scheduleDO) {
        if (scheduleDO == null) {
            return false;
        }
        if (scheduleDO.getDate() != null && scheduleDO.getModCount() > 0) {
            scheduleDO.setDate(TimeAddEight.formatTimeEight(scheduleDO.getDate()));
        }

        boolean b = scheduleService.saveOrUpdate(scheduleDO);
        this.scheduleId = scheduleDO.getId();
        this.product = scheduleDO.getProductNum();
        this.finished = scheduleDO.getFinished();
        this.date = scheduleDO.getDate();
        this.lossNum = scheduleDO.getLossNum();
        return b;
    }

    @PutMapping("/order")
    public Boolean updateSchedule() {
        //订单
        OrderDO orderDO = new OrderDO();
        orderDO.setProductNum(product);
        orderDO.setFinished(finished);
        orderDO.setDate(date);
        orderDO.setLossNum(lossNum);
        orderService.update(orderDO, new QueryWrapper<OrderDO>().eq("schedule_id", this.scheduleId));
        List<OrderDO> orders = orderService.list(new QueryWrapper<OrderDO>().eq("schedule_id", this.scheduleId));
        List<Long> collect = orders.stream().map(data -> data.getId()).collect(Collectors.toList());
        //非成品仓库
        if (scheduleId != null) {
            WarehouseDO warehouseDO = new WarehouseDO();
            warehouseDO.setDeliveryQuantity(lossNum.toString());
            warehouseService.update(warehouseDO,new QueryWrapper<WarehouseDO>().eq("order_id",collect.get(0)));
        }
        //成品仓库
        EndProductWarehouseDO endProductWarehouseDO = new EndProductWarehouseDO();
        endProductWarehouseDO.setProductNum(product);
        endProductWarehouseDO.setEndProductPos(product.toString());
        endProductWarehouseDO.setOrderId(collect.get(0).toString());
        endProductWarehouseDO.setWarehouseNo(dateFormat.format(new Date()));
        return endProductWarehouseService.saveOrUpdate(endProductWarehouseDO, new QueryWrapper<EndProductWarehouseDO>().eq("order_id", collect.get(0).toString()));
    }

    @ApiOperation(value = "删除排期(批量))")
    @DeleteMapping("")
    public Boolean deleteScheduleByIds(@RequestBody List<Long> idList) {
        return scheduleService.removeByIds(idList);
    }

    @ApiOperation(value = "删除排期(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteScheduleById(@PathVariable Long id) {
        return scheduleService.removeById(id);
    }


}
