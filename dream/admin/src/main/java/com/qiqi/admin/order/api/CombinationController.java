package com.qiqi.admin.order.api;

import com.qiqi.order.entity.CombinationDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.order.entity.NailsDO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.order.service.CombinationService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 组合 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api("组合相关接口")
@RestController
@RequestMapping("/combination")
public class CombinationController {

    @Resource
    private CombinationService combinationService;

    @GetMapping("/list")
    public List<CombinationDO> getList(){
        return combinationService.list();
    }

    @ApiOperation(value = "获取组合(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<CombinationDO> getCombinationPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                        @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<CombinationDO> iPage = combinationService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<CombinationDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取组合(单个)")
    @GetMapping("/{id}")
    public CombinationDO getCombination(@PathVariable Long id) {
        //todo: 需要转Vo

        return combinationService.getById(id);
    }

    @ApiOperation(value = "修改组合")
    @PutMapping("")
    public Boolean updateCombination(@RequestBody CombinationDO combinationDO) {
        return combinationService.updateById(combinationDO);
    }

    @ApiOperation(value = "新增组合")
    @PostMapping("")
    public Boolean saveCombination(@RequestBody CombinationDO combinationDO) {
        return combinationService.save(combinationDO);
    }

    @ApiOperation(value = "删除组合(批量))")
    @DeleteMapping("")
    public Boolean deleteCombinationByIds(@RequestBody List<Long> idList) {
        return combinationService.removeByIds(idList);
    }

    @ApiOperation(value = "删除组合(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteCombinationById(@PathVariable Long id) {
        return combinationService.removeById(id);
    }
}
