package com.qiqi.basicdata.service.impl;

import com.qiqi.basicdata.entity.CustomerInformationDO;
import com.qiqi.basicdata.service.CustomerInformationService;
import com.qiqi.basicdata.mapper.CustomerInformationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户资料 服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-15
 */
@Service
public class CustomerInformationServiceImpl extends ServiceImpl<CustomerInformationMapper, CustomerInformationDO> implements CustomerInformationService {

}
