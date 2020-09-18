package com.qiqi.admin.order.api;

import com.qiqi.order.entity.TextureOfMaterialDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.order.service.TextureOfMaterialService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 材质 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-17
 */
@Api("材质相关接口")
@RestController
@RequestMapping("/texture/of/material")
public class TextureOfMaterialController {

    @Resource
    private TextureOfMaterialService textureOfMaterialService;

    @GetMapping("/list")
    public List<TextureOfMaterialDO> getList(){
        return textureOfMaterialService.list();
    }

    @ApiOperation(value = "获取材质(列表)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "page",value = "当前页",required = true,dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "count", value = "当前页个数",required = true,dataType = "Long")
    })
    @GetMapping("")
    public PageEntity<TextureOfMaterialDO> getTextureOfMaterialPage(@RequestParam(value = "page",defaultValue = "1") Long page,
                                        @RequestParam(value = "count",defaultValue = "10") Long count) {
        IPage<TextureOfMaterialDO> iPage = textureOfMaterialService.page(new Page<>(page,count));
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<TextureOfMaterialDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取材质(单个)")
    @GetMapping("/{id}")
    public TextureOfMaterialDO getTextureOfMaterial(@PathVariable Long id) {
        //todo: 需要转Vo

        return textureOfMaterialService.getById(id);
    }

    @ApiOperation(value = "修改材质")
    @PutMapping("")
    public Boolean updateTextureOfMaterial(@RequestBody TextureOfMaterialDO textureOfMaterialDO) {
        return textureOfMaterialService.updateById(textureOfMaterialDO);
    }

    @ApiOperation(value = "新增材质")
    @PostMapping("")
    public Boolean saveTextureOfMaterial(@RequestBody TextureOfMaterialDO textureOfMaterialDO) {
        return textureOfMaterialService.save(textureOfMaterialDO);
    }

    @ApiOperation(value = "删除材质(批量))")
    @DeleteMapping("")
    public Boolean deleteTextureOfMaterialByIds(@RequestBody List<Long> idList) {
        return textureOfMaterialService.removeByIds(idList);
    }

    @ApiOperation(value = "删除材质(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteTextureOfMaterialById(@PathVariable Long id) {
        return textureOfMaterialService.removeById(id);
    }
}
