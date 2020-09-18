package com.qiqi.admin.basicdata.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.basicdata.dto.CommonBoxDTO;
import com.qiqi.basicdata.entity.CommonBoxDO;
import com.qiqi.basicdata.service.BoxTypeSettingService;
import com.qiqi.basicdata.service.CommonBoxService;
import com.qiqi.common.entity.PageEntity;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 常用箱资料管理
 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Api("常用箱资料管理相关接口")

@RestController
@RequestMapping("/common/box")
public class CommonBoxController {

    @Resource
    private CommonBoxService commonBoxService;



    @ApiOperation(value = "获取常用箱资料管理(列表)")
    @PostMapping("/list")
    public PageEntity<CommonBoxDO> getCommonBoxPage(@RequestBody CommonBoxDTO query) {
        QueryWrapper<CommonBoxDO> queryWrapper = new QueryWrapper<CommonBoxDO>()
                .like(StringUtils.isNotBlank(query.getCode()),"code",query.getCode())
                .like(StringUtils.isNotBlank(query.getShorts()),"shorts",query.getShorts())
                .like(StringUtils.isNotBlank(query.getFullName()),"full_name",query.getFullName())
                .like(StringUtils.isNotBlank(query.getCommonBoxManagement()),"common_box_management",query.getCommonBoxManagement());
        IPage<CommonBoxDO> iPage = commonBoxService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(), Convert.convert(new TypeReference<List<CommonBoxDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取常用箱资料管理(单个)")

    @GetMapping("/{id}")
    public CommonBoxDO getCommonBox(@PathVariable Long id) {
        //todo: 需要转Vo

        return commonBoxService.getById(id);
    }

    @ApiOperation(value = "修改常用箱资料管理")

    @PutMapping("")
    public Boolean updateCommonBox(@RequestBody CommonBoxDO commonBoxDO) {
        return commonBoxService.updateById(commonBoxDO);
    }

    @ApiOperation(value = "新增常用箱资料管理")

    @PostMapping("")
    public Boolean saveCommonBox(@RequestBody CommonBoxDO commonBoxDO) {
        return commonBoxService.saveOrUpdate(commonBoxDO);
    }

    @ApiOperation(value = "删除常用箱资料管理(批量))")

    @DeleteMapping("")
    public Boolean deleteCommonBoxByIds(@RequestBody List<Long> idList) {
        return commonBoxService.removeByIds(idList);
    }

    @ApiOperation(value = "删除常用箱资料管理(单个))")

    @DeleteMapping("/{id}")
    public Boolean deleteCommonBoxById(@PathVariable Long id) {
        return commonBoxService.removeById(id);
    }
}
