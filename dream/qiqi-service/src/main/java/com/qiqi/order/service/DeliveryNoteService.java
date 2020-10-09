package com.qiqi.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiqi.order.entity.DeliveryNoteDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiqi.order.vo.DeliveryVO;

/**
 * <p>
 * 送货单 服务类
 * </p>
 *
 * @author QiQiDream
 * @since 2020-10-08
 */
public interface DeliveryNoteService extends IService<DeliveryNoteDO> {

    /**
     * 查询
     * @param objectPage
     * @param signs
     * @param name
     * @return
     */
    IPage<DeliveryVO> getList(Page<Object> objectPage, String signs, String name);

}
