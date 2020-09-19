package com.qiqi.admin.order.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hc
 * @since 2020-09-18
 */
public class TimeAddEight {
    public static Date formatTimeEight(Date d) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        long rightTime = (long) (d.getTime() + 24 * 60 * 60 * 1000);
        Date date = new Date();
        date.setTime(rightTime);
        String newtime = sd.format(date);
        Date date2 = null;
        try {
            date2 = sd.parse(newtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }
}
