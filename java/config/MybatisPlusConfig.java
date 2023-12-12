package com.dlmu.medicine_take_out.config;

//分页插件的配置：通过拦截器来实现

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    //加Bean注解表示的是要Spring管理这个配置类
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        //定义出mybatis拦截器的对象
        MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();
        //假如相关拦截器的插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        return mybatisPlusInterceptor;
    }

}
