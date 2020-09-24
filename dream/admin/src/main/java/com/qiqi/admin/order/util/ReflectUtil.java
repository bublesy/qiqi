package com.qiqi.admin.order.util;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * @author hc
 * @desc
 * @since 2020-09-24
 */
public class ReflectUtil {
    static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    public static Object getTarget(Object dest, Map<String, Object> addProperties) {
        // get property map
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(dest);
        Map<String, Class> propertyMap = Maps.newHashMap();
        for (PropertyDescriptor d : descriptors) {
            if (!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }
        // add extra properties
        for (Map.Entry<String, Object> entry : addProperties.entrySet()) {
            propertyMap.put(entry.getKey(), entry.getValue().getClass());
        }
//        addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
        // new dynamic bean
        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), propertyMap);
        // add old value
        for (Map.Entry<String, Class> entry : propertyMap.entrySet()) {
            try {
                // filter extra properties
                if (!addProperties.containsKey(entry.getKey())) {
                    dynamicBean.setValue(entry.getKey(), propertyUtilsBean.getNestedProperty(dest, entry.getKey()));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        ;
        // add extra value
        for (Map.Entry<String, Object> entry : addProperties.entrySet()) {
            try {
                dynamicBean.setValue(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        ;
        Object target = dynamicBean.getTarget();
        return target;
    }
}
