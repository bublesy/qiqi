package com.qiqi.admin.supplier.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiqi.admin.supplier.model.PaperboardDataSettingVO;
import com.qiqi.basicdata.service.BoxTypeSettingService;
import com.qiqi.supplier.entity.PaperboardDataSettingDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.supplier.service.PaperboardDataSettingService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 纸板资料设定 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Api("纸板资料设定相关接口")
@RestController
@RequestMapping("/paperboard/data/setting")
public class PaperboardDataSettingController {

    @Resource
    private PaperboardDataSettingService paperboardDataSettingService;


    @ApiOperation(value = "获取纸板资料设定(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("/list")
    public PageEntity<PaperboardDataSettingVO> getPaperboardDataSettingPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                                                            @RequestParam(value = "size",defaultValue = "10") Long size,
                                                                            @RequestParam(value = "code") String code,
                                                                            @RequestParam(value = "name") String name,
                                                                            @RequestParam(value = "time") String time) {
        //todo: 需要转Vo
        LambdaQueryWrapper<PaperboardDataSettingDO> wrapper = new LambdaQueryWrapper<PaperboardDataSettingDO>();
        wrapper.like(!ObjectUtils.isEmpty(time),PaperboardDataSettingDO::getCreatedTime,time);
        wrapper.like(!ObjectUtils.isEmpty(name),PaperboardDataSettingDO::getName,name);
        wrapper.like(!ObjectUtils.isEmpty(code),PaperboardDataSettingDO::getCode,code);
        IPage<PaperboardDataSettingDO> iPage = paperboardDataSettingService.page(new Page<>(page,size),wrapper);
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<PaperboardDataSettingVO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取纸板资料设定(单个)")
    @GetMapping("/{id}")
    public PaperboardDataSettingDO getPaperboardDataSetting(@PathVariable Long id) {
        //todo: 需要转Vo

        return paperboardDataSettingService.getById(id);
    }

    @ApiOperation(value = "修改纸板资料设定")
    @PutMapping("")
    public Boolean updatePaperboardDataSetting(@RequestBody PaperboardDataSettingDO paperboardDataSettingDO) {
        return paperboardDataSettingService.updateById(paperboardDataSettingDO);
    }

    @ApiOperation(value = "新增纸板资料设定")
    @PostMapping("/add")
    public Boolean savePaperboardDataSetting(@RequestBody PaperboardDataSettingDO paperboardDataSettingDO) {
        return paperboardDataSettingService.saveOrUpdate(paperboardDataSettingDO);
    }

    @ApiOperation(value = "删除纸板资料设定(批量))")
    @DeleteMapping("")
    public Boolean deletePaperboardDataSettingByIds(@RequestBody List<Long> idList) {
        return paperboardDataSettingService.removeByIds(idList);
    }

    @ApiOperation(value = "删除纸板资料设定(单个))")
    @DeleteMapping("/{id}")
    public Boolean deletePaperboardDataSettingById(@PathVariable Long id) {
        return paperboardDataSettingService.removeById(id);
    }
}
