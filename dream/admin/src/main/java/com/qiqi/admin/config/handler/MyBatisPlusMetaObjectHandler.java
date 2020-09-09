package com.qiqi.admin.config.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.qiqi.security.util.UserUtil;
import com.qiqi.sys.entity.SysUserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * mybatis-plus自动填充配置
 *
 * @author QiQiDream
 * @since 2019/4/16 15:48
 */
@Slf4j
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String deleted = "deleted";
        if (metaObject.hasSetter(deleted)) {
            Date date = new Date();

            SysUserDO loginUser = UserUtil.getLoginUserInfo();
            Long userId = ObjectUtils.isEmpty(loginUser)?null:loginUser.getId();

            this.setFieldValByName("deleted", false, metaObject);
            this.setFieldValByName("updatedTime", date, metaObject);
            this.setFieldValByName("updatedBy", userId, metaObject);
            this.setFieldValByName("createdTime", date, metaObject);
            this.setFieldValByName("createdBy", userId, metaObject);
        }


        String sort = "sort";
        if (metaObject.hasSetter(sort) && null == metaObject.getValue(sort)) {
            this.setFieldValByName("sort", 999, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String deleted = "updatedTime";
        if (metaObject.hasSetter(deleted)) {
            SysUserDO loginUser = UserUtil.getLoginUserInfo();
            Long userId = ObjectUtils.isEmpty(loginUser)?null:loginUser.getId();

            this.setFieldValByName("updatedTime", new Date(), metaObject);
            this.setFieldValByName("updatedBy", userId, metaObject);
        }
    }
}
