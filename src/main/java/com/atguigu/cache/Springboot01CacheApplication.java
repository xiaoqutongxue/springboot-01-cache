package com.atguigu.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
/**
 * 搭建缓存基本环境
 * 1.导入数据库文件，创建department和employee表
 * 2.创建javabean封装数据
 * 3.操作数据库
 *      1.配置数据源
 *      2.使用注解版mybatis
 *          1.@MapperScan指定扫描mapper接口所在的包
 *
 *
 * 快速体验缓存
 *      步骤：
 *          1.开启基于注解的缓存@EnableCaching
 *          2.标记缓存注解
 */
@EnableCaching
@MapperScan("com.atguigu.cache.mapper")
public class Springboot01CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot01CacheApplication.class, args);
    }

}
