package com.qiqi.admin.basicdata.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiqi.basicdata.dto.BoxTypeSettingDTO;
import com.qiqi.basicdata.entity.BoxTypeSettingDO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qiqi.basicdata.service.BoxTypeSettingService;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 箱型设定 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Api("箱型设定相关接口")
@RestController
@RequestMapping("/box/type/setting")
public class BoxTypeSettingController {

    @Resource
    private BoxTypeSettingService boxTypeSettingService;

    @ApiOperation(value = "获取箱型设定(列表)")
    @PostMapping("/list")
    public PageEntity<BoxTypeSettingDO> getBoxTypeSettingPage(@RequestBody BoxTypeSettingDTO query) {
        QueryWrapper<BoxTypeSettingDO> queryWrapper = new QueryWrapper<BoxTypeSettingDO>()
                .like(StringUtils.isNotBlank(query.getCode()),"code",query.getCode())
                .like(StringUtils.isNotBlank(query.getName()),"name",query.getName())
                .eq("limit_paper_length",query.getLimitPaperLength());

        IPage<BoxTypeSettingDO> iPage = boxTypeSettingService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<BoxTypeSettingDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "不分页")
    @GetMapping("/list")
    public List<BoxTypeSettingDO> getList(){
        return boxTypeSettingService.list();
    }

    @ApiOperation(value = "获取箱型设定(单个)")
    @GetMapping("/{id}")
    public BoxTypeSettingDO getBoxTypeSetting(@PathVariable Long id) {
        //todo: 需要转Vo

        return boxTypeSettingService.getById(id);
    }

    @ApiOperation(value = "修改箱型设定")
    @PutMapping("")
    public Boolean updateBoxTypeSetting(@RequestBody BoxTypeSettingDO boxTypeSettingDO) {
        return boxTypeSettingService.updateById(boxTypeSettingDO);
    }

    @ApiOperation(value = "新增箱型设定")
    @PostMapping("")
    public Boolean saveBoxTypeSetting(@RequestBody BoxTypeSettingDO boxTypeSettingDO) {
        return boxTypeSettingService.saveOrUpdate(boxTypeSettingDO);
    }

    @ApiOperation(value = "删除箱型设定(批量))")
    @DeleteMapping("")
    public Boolean deleteBoxTypeSettingByIds(@RequestBody List<Long> idList) {
        return boxTypeSettingService.removeByIds(idList);
    }

    @ApiOperation(value = "删除箱型设定(单个))")
    @DeleteMapping("/{id}")
    public Boolean deleteBoxTypeSettingById(@PathVariable Long id) {
        return boxTypeSettingService.removeById(id);
    }
}
