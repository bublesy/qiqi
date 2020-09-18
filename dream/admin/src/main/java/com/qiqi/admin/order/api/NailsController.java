package com.qiqi.admin.order.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.order.dto.NailsDTO;
import com.qiqi.order.entity.ColourDO;
import com.qiqi.order.entity.NailsDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.order.service.NailsService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 钉类 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api("钉类相关接口")
@RestController
@RequestMapping("/nails")
public class NailsController {

    @Resource
    private NailsService nailsService;

    @ApiOperation(value = "获取钉类(列表)")
    @PostMapping("all")
    public PageEntity<NailsDO> getNailsPage(@RequestBody NailsDTO query) {
        IPage<NailsDO> iPage = nailsService.page(new Page<>(query.getPage(),query.getCount()));
        //todo: 需要转Vo
        QueryWrapper<NailsDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(query.getName()),"name",query.getName());
        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<NailsDO>>() {}, iPage.getRecords()));
    }

    @GetMapping("/list")
    public List<NailsDO> getList(){
        return nailsService.list();
    }

    @ApiOperation(value = "获取钉类(单个)")
    @GetMapping("/{id}")
    public NailsDO getNails(@PathVariable Long id) {
        //todo: 需要转Vo

        return nailsService.getById(id);
    }

    @ApiOperation(value = "修改钉类")
    @PutMapping("")
    public Boolean updateNails(@RequestBody NailsDO nailsDO) {
        return nailsService.updateById(nailsDO);
    }

    @ApiOperation(value = "新增钉类")
    @PostMapping("")
    public Boolean saveNails(@RequestBody NailsDO nailsDO) {
        return nailsService.save(nailsDO);
    }

    @ApiOperation(value = "删除钉类(批量))")
    @DeleteMapping("")
    public Boolean deleteNailsByIds(@RequestBody List<Long> idList) {
        return nailsService.removeByIds(idList);
    }

    @ApiOperation(value = "删除钉类(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteNailsById(@PathVariable Long id) {
        return nailsService.removeById(id);
    }
}
