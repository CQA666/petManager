package com.pet.petmanager.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

        //创建一个实例
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //添加分页插件到拦截器中
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //返回拦截器
        return interceptor;

    }
    // 定义一个全局配置的Bean
    @Bean
    // 创建全局配置实例
    public GlobalConfig globalConfig() {
        // 实例化GlobalConfig对象(GlobalConfig是Mybatisplus提供的全局缓存配置类)
        GlobalConfig globalConfig = new GlobalConfig();

        // 创建并配置DbConfig(DbConfig用于存储和管理数据库配置的类)
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        // 设置数据库主键生成策略为自增
        dbConfig.setIdType(IdType.AUTO);

        // 将DbConfig设置到GlobalConfig
        globalConfig.setDbConfig(dbConfig);

        // 返回全局配置实例
        return globalConfig;
    }

}
