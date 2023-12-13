package com.dlmu.medicine_take_out;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
//@EnableTransactionManagement 表示的是开启了事务注解的支持
public class yaojisong8Application {
    public static void main(String[] args) {
        SpringApplication.run(yaojisong8Application.class,args);
        log.info("项目启动成功");
    }
}
