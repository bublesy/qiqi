package com.qiqi.admin.order.api;

import com.qiqi.order.entity.PrintLayoutDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<PrintLayoutDO> getPrintLayoutPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                        @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<PrintLayoutDO> iPage = printLayoutService.page(new Page<>(page,count));
        //todo: 需要转Vo

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
