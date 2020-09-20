package com.qiqi.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author hc
 * @since 2020-09-20
 */
@Configuration
public class SpringMvcConfigurationInitializer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String filePath = this.getClass().getResource("/").getPath() +  "static";
//        filePath = filePath.substring(1, filePath.length());
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///"+filePath+"/upload/");
    }
}