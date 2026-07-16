package com.pet.petmanager.config;

import com.pet.petmanager.utils.FileUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

// 定义一个配置类，实现WebMvcConfigurer接口以便配置Spring MVC
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

    // 重写WebMvcConfigurer接口中的addResourceHandlers方法，用于配置静态资源处理
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 定义一个Path对象，用于存储项目根路径
        Path projectRootPath = null;
        // 将项目根路径设置为指定的基础路径
        projectRootPath = Path.of(FileUtil.FILE_BASE_PATH);
        // 定义图片文件夹的路径，基于项目根路径并解析出"img"目录的绝对路径
        String imgFolderPath = projectRootPath.resolve("img").toAbsolutePath().toString();
        // 添加资源处理器，将"/img/**"路径映射到指定的文件夹位置，用于访问静态图片资源
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + imgFolderPath + "/");
    }
}
