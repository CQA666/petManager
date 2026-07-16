package com.pet.petmanager.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 定义配置类，实现WebMvcConfigurer接口，用于配置Web MVC相关的设置
@Configuration
public class MultipartConfig implements WebMvcConfigurer {
    // 定义一个Bean，返回类型为MultipartConfigElement，用于配置文件上传的设置
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        // 创建MultipartConfigFactory实例，用于生成文件上传配置
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置上传单个文件的最大大小为10MB
        factory.setMaxFileSize(DataSize.parse("10MB")); // 文件大小限制
        // 设置上传请求的最大大小为20MB，包括文件和表单数据
        factory.setMaxRequestSize(DataSize.parse("20MB")); // 请求大小限制
        // 使用工厂生成并返回MultipartConfigElement配置实例
        return factory.createMultipartConfig();
    }
}