package com.qiqi.admin.basicdata.api;

import com.qiqi.basicdata.entity.VendorDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.basicdata.service.VendorService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-29
 */
@Api("相关接口")
@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Resource
    private VendorService vendorService;

    @ApiOperation(value = "获取(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<VendorDO> getVendorPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                        @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<VendorDO> iPage = vendorService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<VendorDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取(单个)")
    @GetMapping("/{id}")
    public VendorDO getVendor(@PathVariable Long id) {
        //todo: 需要转Vo

        return vendorService.getById(id);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Boolean updateVendor(@RequestBody VendorDO vendorDO) {
        return vendorService.updateById(vendorDO);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Boolean saveVendor(@RequestBody VendorDO vendorDO) {
        return vendorService.save(vendorDO);
    }

    @ApiOperation(value = "删除(批量))")
    @DeleteMapping("")
    public Boolean deleteVendorByIds(@RequestBody List<Long> idList) {
        return vendorService.removeByIds(idList);
    }

    @ApiOperation(value = "删除(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteVendorById(@PathVariable Long id) {
        return vendorService.removeById(id);
    }
}
