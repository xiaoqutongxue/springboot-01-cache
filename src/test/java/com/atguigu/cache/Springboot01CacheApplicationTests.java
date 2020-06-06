package com.atguigu.cache;

import com.atguigu.cache.bean.Employee;


import com.atguigu.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot01CacheApplicationTests {


    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate; // 操作字符串的

    @Autowired
    RedisTemplate redisTemplate; // k-v 都是操作对象的

    @Autowired
    RedisTemplate<Object, Employee> employeeRedisTemplate;

    @Test
    public void contextLoads() {
        Employee empById = employeeMapper.getEmpById(1);
        System.out.println(empById.toString());
    }


    /**
     * 字符串，list，set，hash，zset（有序集合）
     */
    @Test
    public void test01() {
        //  stringRedisTemplate.opsForValue();  操作字符串的
        //  相当于给redis中增加数据
        stringRedisTemplate.opsForValue().append("testMsg","xiaoqu");

        // 读数据
        //String msg = stringRedisTemplate.opsForValue().get("testMsg");
        //System.out.println("msg" + msg);
    }

    /**
     * 测试保存对象
     */
    @Test
    public void test02() {
        Employee empById = employeeMapper.getEmpById(1);
        // 默认如果保存对象，使用jdk序列化机制，序列化后的数据保存在redis中，但是会乱码
        // redisTemplate.opsForValue().set("emp",empById);

        // 将数据以json的方式保存
            // redisTemplate默认的序列化规则

        // 改变默认的序列化规则
        employeeRedisTemplate.opsForValue().set("emp",empById);
    }
}
