package com.qiqi.generation;

import com.baomidou.mybatisplus.generator.AutoGenerator;

/**
 * Mysql代码生成器
 * @author QiQiDream
 * @date 2020/6/29
 */
public class MysqlGenerator extends SuperGenerator {

    public void generator(String tableName) {

        // 代码生成器
        AutoGenerator mpg = getAutoGenerator(tableName,false);
        mpg.execute();
        if (tableName == null) {
            System.err.println(" 自动生成代码成功 !");
        } else {
            System.err.println(" 表【 " + tableName + " 】" + "自动生成代码成功 !");

        }
    }

    public static void main(String[] args) {
        MysqlGenerator aa = new MysqlGenerator();
        aa.generator("purchase_order");
    }
}
