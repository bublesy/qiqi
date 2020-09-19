package com.qiqi.admin.staff.api;

import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiqi.admin.staff.dto.PersonnerDTO;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.common.entity.PageEntity;
import com.qiqi.staff.entity.PersonnelDO;
import com.qiqi.staff.service.PersonnelService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 人事档案管理 前端控制器
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-19
 */
@Api("人事档案管理相关接口")
@RestController
@RequestMapping("/personnel")
public class PersonnelController {

    @Resource
    private PersonnelService personnelService;

    @ApiOperation(value = "获取人事档案管理(列表)")
    @PostMapping(".all")
    public PageEntity<PersonnelDO> getPersonnelPage(@RequestBody PersonnerDTO query) {
        QueryWrapper<PersonnelDO> queryWrapper = new QueryWrapper<>();
        IPage<PersonnelDO> iPage = personnelService.page(new Page<>(query.getPage(),query.getCount()),queryWrapper);
        //todo: 需要转Vo

        return new PageEntity<>(iPage.getTotal(),Convert.convert(new TypeReference<List<PersonnelDO>>() {}, iPage.getRecords()));
    }

    @ApiOperation(value = "获取人事档案管理(单个)")
    @GetMapping("/{id}")
    public PersonnelDO getPersonnel(@PathVariable Long id) {
        //todo: 需要转Vo

        return personnelService.getById(id);
    }

    @ApiOperation(value = "修改人事档案管理")
    @PutMapping("")
    public Boolean updatePersonnel(@RequestBody PersonnelDO personnelDO) {
        return personnelService.updateById(personnelDO);
    }

    @ApiOperation(value = "新增人事档案管理")
    @PostMapping("")
    public Boolean savePersonnel(@RequestBody PersonnelDO personnelDO) {
        return personnelService.save(personnelDO);
    }

    @ApiOperation(value = "删除人事档案管理(批量))")
    @DeleteMapping("/all")
    public Boolean deletePersonnelByIds(@RequestBody List<Long> idList) {
        return personnelService.removeByIds(idList);
    }

    @ApiOperation(value = "删除人事档案管理(单个))")
    @DeleteMapping("/{id}")
    public Boolean deletePersonnelById(@PathVariable Long id) {
        return personnelService.removeById(id);
    }
}
