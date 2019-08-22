package com.miaoshaproject;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
/*
@EnableAutoConfiguration:
将启动类当成自动化可以配置的Bean，并且能够可以开启整个工程基于Spring的自动化配置，开启自动装配
 */

//@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.miaoshaproject"})
@RestController
@MapperScan("com.miaoshaproject.dao")
public class App {

    @Autowired
    private UserDOMapper userDOMapper;


    @RequestMapping("/")
    public String home() {
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if (userDO == null) {
            return "用户对象不存在";
        }
        return userDO.getName();
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        /*
        启动Spring的项目:SpringApplication.run(当前类的.class,main方法的参数(args))
        配置成功:不需要外力，Spring自动启动tomcat容器
         */
        SpringApplication.run(App.class, args);
    }
}
