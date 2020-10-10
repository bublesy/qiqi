package com.qiqi.finance.service.impl;

import com.qiqi.finance.entity.CustomerDetailDO;
import com.qiqi.finance.mapper.CustomerDetailMapper;
import com.qiqi.finance.service.CustomerDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiqi.order.vo.WarningVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-26
 */
@Service
public class CustomerDetailServiceImpl extends ServiceImpl<CustomerDetailMapper, CustomerDetailDO> implements CustomerDetailService {

    @Resource
    CustomerDetailMapper customerDetailMapper;

    @Override
    public List<WarningVO> getWarning() {
        return customerDetailMapper.getWarning();
    }
}
