package com.qiqi.admin.order.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

/**
 * 唯一订单号实现
 * @author hc
 * @since 2020-08-15
 */
@Slf4j
@Component
public class IdGeneratorUtils {

    private long workerId;
    private long dataCenterId = 0L;
    private long sequence = 0L;
    private long workerIdBits = 8L;
    private long sequenceBits = 12L;
    private long workerIdShift = sequenceBits;
    private long dataCenterIdShift = sequenceBits + workerIdBits;
    private long sequenceMask = -1L ^ ( -1L << sequenceBits );
    private long lastTimestamp;
    
    public IdGeneratorUtils(){
        workerId = 0x000000FF & getLastIp();
    }

    public synchronized String nextId(){
        long timestamp = currentTime();
        if(timestamp < lastTimestamp){
            throw new RuntimeException(String.format("时钟向后移动。拒绝在%d毫秒内生成id", lastTimestamp - timestamp));
        }
        if(lastTimestamp == timestamp){
            sequence = (sequence + 1) & sequenceMask;
            if(sequence == 0){
                timestamp = nextMillis(lastTimestamp);
            }
        }else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        long suffix = (dataCenterId << dataCenterIdShift) | (workerId << workerIdShift) | sequence;

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHMMssSSS");
        String dataPrefix = format.format(timestamp);
        String prefix = dataPrefix.substring(14,dataPrefix.length());
        return prefix + suffix;
    }

    protected long nextMillis(long lastTimestamp){
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp){
            timestamp = currentTime();
        }
        return timestamp;
    }

    protected long currentTime(){
        return System.currentTimeMillis();
    }

    private int getLastIp() {
        byte lastIp = 0;
        try{
            InetAddress ip = InetAddress.getLocalHost();
            byte[] ipByte = ip.getAddress();
            lastIp = ipByte[ipByte.length - 1];
        }catch (UnknownHostException e){
            log.info("获取当前IP失败");
            e.printStackTrace();
        }
        return lastIp;
    }

    public static void main(String[] args) {
        IdGeneratorUtils idGeneratorUtils = new IdGeneratorUtils();
        String s = idGeneratorUtils.nextId();
        System.out.println(s);
    }
}
