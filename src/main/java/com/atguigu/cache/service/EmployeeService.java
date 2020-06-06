package com.atguigu.cache.service;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

// @CacheConfig:抽取缓存的公共配置
@CacheConfig(cacheNames = "emp",cacheManager = "employeeCacheManager")
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**@Cacheable
     * 将方法的运行结果进行缓存，以后再要相同的数据，直接从缓存中获取，不需要调用方法
     * CacheableManager管理多个cache组件，对缓存真正的CRUD操作再cache组件中，每一个缓存组件有自己唯一一个名字
     * @Cacheable中的几个属性
     *      cacheNames/value（这个两个用谁都行，效果是一样的） :指定缓存组件名字，将方法返回值放在哪个缓存中，是数组的方式，可以指定多个缓存
     *      key:缓存数据使用的key，可以用它指定。默认使用的方法参数值   其中的对应的key-value中的value是方法返回值
     *          编写SpEL #id:代表的就是参数id的值
     *      keyGenerator:key生成器，可以自己指定key的生成器组件id
     *                  keyGenerator/key 二选一;        调用自己写的key规则keyGenerator = "MyKeyGenerator"
     *      cacheManager：缓存管理器；或者cacheResolver指定获取解析器
     *      condition：指定符合条件的情况下才进行缓存
     *      condition = "a0>1" =  condition = "#id > 1"  :第一个参数大于1的时候才进行缓存
     *      unless：否定缓存：当unless指定的条件是true，方法返回值不会被缓存
     *      sync:是否异步模式
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "emp")
    public Employee getEmp(Integer id){
        System.out.println("进入到service查询方法，查询" + id + "号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    /**
     * @CachePut:即调用方法，又更新缓存
     * 1.运行时机
     *    1.先运行目标方法
     *    2.将目标方法的结果缓存
     *
     * 注意：
     *  key = #employee.id:使用传入参数的id，不然找不到对应缓存的key，无法真正更新缓存
     *  key = #result.id:适用返回值的id  同上的效果是一样的
     *  @Cacheable不能使用#result，因为他是先判断在执行方法
     */
    @CachePut(value = "emp",key = "#result.id")
    public Employee updateEmp(Employee employee){
        System.out.println("进入到service更新方法:" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict:缓存清除
     * allEntries = true意思是清除所有的缓存
     * beforeInvocation = false；缓存的清除是否在方法之前
     *      默认是在方法执行之后
     */
    @CacheEvict(value = "emp",key = "#Id",allEntries = true)
    public void delEmp(Integer Id){
        System.out.println("进入到service删除方法:" + Id);
        // employeeMapper.deleteEmpById(Id);
    }

    // 定义复杂的缓存规则
    @Caching(
            cacheable = {
                    @Cacheable(value = "emp",key = "#lastName")
            },
            put = {
                    @CachePut(value = "emp",key = "#result.email"),
                    @CachePut(value = "emp",key = "#result.id")
            }
    )
    public Employee getEmpByLastName(String lastName){

        Employee empByLastName = employeeMapper.getEmpByLastName(lastName);
        return empByLastName;
    }
}
