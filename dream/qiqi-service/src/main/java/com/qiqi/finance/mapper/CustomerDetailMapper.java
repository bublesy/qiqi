package com.qiqi.finance.mapper;

import com.qiqi.finance.entity.CustomerDetailDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiqi.order.vo.WarningVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-09-26
 */
public interface CustomerDetailMapper extends BaseMapper<CustomerDetailDO> {

    List<WarningVO> getWarning(Integer orderDay);
}
