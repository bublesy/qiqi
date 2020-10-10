package com.qiqi.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.order.entity.DeliveryNoteDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiqi.order.vo.DeliveryVO;
import com.qiqi.order.vo.SendDeliveryVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 送货单 Mapper 接口
 * </p>
 *
 * @author QiQiDream
 * @since 2020-10-08
 */
public interface DeliveryNoteMapper extends BaseMapper<DeliveryNoteDO> {

    /**
     * 查询
     * @param objectPage
     * @param signs
     * @param name
     * @return
     */
    IPage<SendDeliveryVO> getList(@Param("objectPage") Page<Object> objectPage, @Param("signs") String signs, @Param("name") String name);
}
