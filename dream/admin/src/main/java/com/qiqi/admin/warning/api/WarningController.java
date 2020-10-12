package com.qiqi.admin.warning.api;

import com.qiqi.warning.entity.WarningDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.warning.service.WarningService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 预警表 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-10-12
 */
@Api("预警相关接口")
@RestController
@RequestMapping("/warning")
public class WarningController {

    @Resource
    private WarningService warningService;

    @ApiOperation(value = "获取预警(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<WarningDO> getWarningPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                        @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<WarningDO> iPage = warningService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<WarningDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取预警(单个)")
    @GetMapping("/{id}")
    public WarningDO getWarning(@PathVariable Long id) {
        //todo: 需要转Vo

        return warningService.getById(id);
    }

    @ApiOperation(value = "修改预警")
    @PutMapping("")
    public Boolean updateWarning(@RequestBody WarningDO warningDO) {
        boolean flag = false;
        List<WarningDO> list = warningService.list();
        if (list.size() == 0){
            flag=warningService.save(warningDO);
        }else{
            flag=warningService.updateById(warningDO);
        }
        return flag;
    }

    @ApiOperation(value = "新增预警")
    @PostMapping("")
    public Boolean saveWarning(@RequestBody WarningDO warningDO) {
        return warningService.save(warningDO);
    }

    @ApiOperation(value = "删除预警(批量))")
    @DeleteMapping("")
    public Boolean deleteWarningByIds(@RequestBody List<Long> idList) {
        return warningService.removeByIds(idList);
    }

    @ApiOperation(value = "删除预警(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteWarningById(@PathVariable Long id) {
        return warningService.removeById(id);
    }
}
