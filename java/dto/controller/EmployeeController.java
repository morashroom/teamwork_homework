package com.dlmu.medicine_take_out.dto.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dlmu.medicine_take_out.entity.Employee;
import com.dlmu.medicine_take_out.service.EmployeeService;
import com.dlmu.medicine_take_out.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工的登录
     * @param request
     * @param emplyee
     * @return
     */


    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee emplyee){
       //步骤一：对密码进行MD5的加密的处理
        String password=emplyee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        //步骤二：查询数据库
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,emplyee.getUsername());
        Employee emp=employeeService.getOne(queryWrapper);
        //步骤三：没有查询到就返回失败的结果
        if(emp==null){
            return R.error("登录失败");
        }
        //
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }
        //如果员工的被封掉了
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }

        //登录成功：将用户的数据保存到session里面
        /**
         * session为每一个用户的浏览器分配的内存空间，具有保存用户信息的功能
         */
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
    request.getSession().removeAttribute("emloyee");
    return R.success("退出成功");
    }

    /**
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}",employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        //获取当前操作用户的id
//        Long empId=(Long) request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);


        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /**
     *
     * @param page  表示的是第几页了
     * @param pageSize  表示的是每一页有几个数据
     * @param name  表示的是查询的结果
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name) {
        log.info("page= {},pageSize={},name={}",page,pageSize,name);

        //构造分页构造器,也就是new出来的Page对象
        Page pageInfo=new Page(page,pageSize);


        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //查询完成之后就会将Page类的对象进行封装
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee) {
        log.info(employee.toString());

        Long empId=(Long)request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);

        //这里表示的是通过id的主键的值取进行相关的修改
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }


    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息。。。");
        Employee employee=employeeService.getById(id);

        if(employee!=null){
            return R.success(employee);//返回的是查询到了的员工的信息
        }else{
            return R.error("没有查到相关员工的信息");
        }
    }


}
