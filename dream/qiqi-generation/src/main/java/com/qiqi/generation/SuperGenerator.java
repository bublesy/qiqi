package com.qiqi.generation;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.qiqi.common.base.BaseModel;

import java.io.File;
import java.util.*;
/**
 * 代码生成器父类
 * @author QiQiDream
 * @date 2020/6/29
 */
public class SuperGenerator {

    /**
     * 获取TemplateConfig
     *
     * @return
     */
    protected TemplateConfig getTemplateConfig() {
        return new TemplateConfig().setXml(null);
    }

    /**
     * 获取InjectionConfig
     *
     * @return
     */
    protected InjectionConfig getInjectionConfig() {

        return new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                this.setMap(map);
            }
        }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig(
                "/templates/mapper.xml.vm") {
            // 自定义输出文件目录
            @Override
            public String outputFile(TableInfo tableInfo) {
                return getResourcePath() + "/mapper/" + tableInfo.getEntityName().replace("DO","") + "Mapper.xml";
            }
        }));
    }

    /**
     * 获取PackageConfig
     *
     * @return
     */
    protected PackageConfig getPackageConfig() {
        return new PackageConfig()
                .setParent("com.qiqi.generator")
                .setController("api")
                .setEntity("entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl");
    }

    /**
     * 获取StrategyConfig
     *
     * @param tableName
     * @return
     */
    protected StrategyConfig getStrategyConfig(String tableName) {
        List<TableFill> tableFillList = getTableFills();
        return new StrategyConfig()
                .setCapitalMode(false)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTableFillList(tableFillList)
                .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setRestControllerStyle(true)
                .setSuperEntityClass(BaseModel.class)
                .setSuperEntityColumns("id","is_deleted","updated_time","updated_by","created_time","created_by")
                .setLogicDeleteFieldName("is_deleted")
//                .setEntityTableFieldAnnotationEnable(true)
                .setInclude(tableName.split(","));
    }

    protected StrategyConfig getStrategyConfig2(String tableName) {
        List<TableFill> tableFillList = getTableFills();
        return new StrategyConfig()
                .setCapitalMode(false)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTableFillList(tableFillList)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
//                .setEntityTableFieldAnnotationEnable(true)
                .setInclude(tableName.split(","));
    }

    /**
     * 获取TableFill策略
     *
     * @return
     */
    protected List<TableFill> getTableFills() {
        return new ArrayList<>();
    }

    /**
     * 获取DataSourceConfig
     *
     * @return
     */
    protected DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {

                        if (fieldType.toLowerCase().equals("date")) {
                            return DbColumnType.DATE;
                        }
                        if (fieldType.toLowerCase().equals("time")) {
                            return DbColumnType.DATE;
                        }
                        if (fieldType.toLowerCase().equals("datetime")) {
                            return DbColumnType.DATE;
                        }
                        if (fieldType.toLowerCase().equals("json")) {
                            return DreamDbColumnType.FAST_JSON;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                })
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("jhls123456")
                .setUrl("jdbc:mysql://192.168.1.190:3306/new_retail?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=UTF-8");
    }

    /**
     * 获取GlobalConfig
     *
     * @return
     */
    protected GlobalConfig getGlobalConfig() {
        return new GlobalConfig()
                //输出目录
                .setOutputDir(getJavaPath())
                // 是否覆盖文件
                .setFileOverride(false)
                // 开启 activeRecord 模式
                .setActiveRecord(false)
                // XML 二级缓存
                .setEnableCache(false)
                // XML ResultMap
                .setBaseResultMap(false)
                // XML columList
                .setBaseColumnList(false)
                //是否生成 kotlin 代码
                .setKotlin(false)
                .setOpen(false)
                //作者
                .setAuthor("QiQiDream")
                //自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName("%sDO")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");
    }


    /**
     * 获取根目录
     *
     * @return
     */
    private String getRootPath() {
        String file = Objects.requireNonNull(SuperGenerator.class.getClassLoader().getResource("")).getFile();
        return new File(file).getParentFile().getParentFile().getParent();
    }

    /**
     * 获取JAVA目录
     *
     * @return
     */
    protected String getJavaPath() {
        String javaPath = getRootPath() + "/qiqi-service/src/main/java";
        return javaPath;
    }

    /**
     * 获取Resource目录
     *
     * @return
     */
    protected String getResourcePath() {
        String resourcePath = getRootPath() + "/qiqi-service/src/main/resources";
        return resourcePath;
    }

    /**
     * 获取AutoGenerator
     *
     * @param tableName
     * @return
     */
    protected AutoGenerator getAutoGenerator(String tableName,Boolean isMiddle) {
        if (isMiddle){
            return new AutoGenerator()
                    // 全局配置
                    .setGlobalConfig(getGlobalConfig())
                    // 数据源配置
                    .setDataSource(getDataSourceConfig())
                    // 策略配置
                    .setStrategy(getStrategyConfig2(tableName))
                    // 包配置
                    .setPackageInfo(getPackageConfig())
                    // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                    .setCfg(getInjectionConfig())
                    .setTemplate(getTemplateConfig());
        }
        return new AutoGenerator()
                // 全局配置
                .setGlobalConfig(getGlobalConfig())
                // 数据源配置
                .setDataSource(getDataSourceConfig())
                // 策略配置
                .setStrategy(getStrategyConfig(tableName))
                // 包配置
                .setPackageInfo(getPackageConfig())
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                .setCfg(getInjectionConfig())
                .setTemplate(getTemplateConfig());
    }

}
