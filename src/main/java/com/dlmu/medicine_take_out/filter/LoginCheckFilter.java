package com.dlmu.medicine_take_out.filter;

import com.alibaba.fastjson.JSON;
import com.dlmu.medicine_take_out.common.BaseContext;
import com.dlmu.medicine_take_out.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //匹配路径的工具类对象判断两个路径是否相同
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //获取本次请求拦截的URL
        String requestURI=request.getRequestURI();

        //定义不需要处理的路径
        String[] urls=new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        boolean check=check(urls,requestURI);

        System.out.println("check的值是: "+check+"urls的值是："+urls);

        if(check){
            filterChain.doFilter(request,response);
            return ;
        }

        //判断用户的登录状态，如果已经登录，就直接放行这个http请求
        if(request.getSession().getAttribute("employee")!=null) {

            Long empId=(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);
            return ;
        }

        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }


        //没有登录的情况，通过输出流的方式向客户端去响应数据。
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls,String requestURI) {
        for(String url:urls) {
            boolean match= PATH_MATCHER.match(url,requestURI);
            if(match) {
                return true;
            }
        }
        return  false;
    }


}
