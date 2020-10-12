package com.qiqi.finance.service;

import com.qiqi.finance.entity.CustomerDetailDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiqi.order.vo.WarningVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-26
 */
public interface CustomerDetailService extends IService<CustomerDetailDO> {

    List<WarningVO> getWarning(Integer orderDay);
}
