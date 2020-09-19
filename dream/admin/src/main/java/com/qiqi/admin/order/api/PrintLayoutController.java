package com.qiqi.admin.order.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.order.dto.PrintLayoutDTO;
import com.qiqi.order.entity.ColourDO;
import com.qiqi.order.entity.PrintLayoutDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.order.service.PrintLayoutService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 印刷版面 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api("印刷版面相关接口")
@RestController
@RequestMapping("/print/layout")
public class PrintLayoutController {

    @Resource
    private PrintLayoutService printLayoutService;

    @GetMapping("/list")
    public List<PrintLayoutDO> getList(){
        return printLayoutService.list();
    }

    @ApiOperation(value = "获取印刷版面(列表)")
    @PostMapping("all")
    public PageEntity<PrintLayoutDO> getPrintLayoutPage(@RequestBody PrintLayoutDTO query) {
        QueryWrapper<PrintLayoutDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(query.getName()),"name",query.getName());
        IPage<PrintLayoutDO> iPage = printLayoutService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<PrintLayoutDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取印刷版面(单个)")
    @GetMapping("/{id}")
    public PrintLayoutDO getPrintLayout(@PathVariable Long id) {
        //todo: 需要转Vo

        return printLayoutService.getById(id);
    }

    @ApiOperation(value = "修改印刷版面")
    @PutMapping("")
    public Boolean updatePrintLayout(@RequestBody PrintLayoutDO printLayoutDO) {
        return printLayoutService.updateById(printLayoutDO);
    }

    @ApiOperation(value = "新增印刷版面")
    @PostMapping("")
    public Boolean savePrintLayout(@RequestBody PrintLayoutDO printLayoutDO) {
        return printLayoutService.save(printLayoutDO);
    }

    @ApiOperation(value = "删除印刷版面(批量))")
    @DeleteMapping("")
    public Boolean deletePrintLayoutByIds(@RequestBody List<Long> idList) {
        return printLayoutService.removeByIds(idList);
    }

    @ApiOperation(value = "删除印刷版面(单个))")
    @DeleteMapping("/{id}")
    public Boolean deletePrintLayoutById(@PathVariable Long id) {
        return printLayoutService.removeById(id);
    }
}
