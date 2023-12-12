package com.dlmu.medicine_take_out.dto.controller;


import com.dlmu.medicine_take_out.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件的上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    //这里表示的是图片所存放的位置
    @Value("${reggie.path}")
    private String basePath;


    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //MultipartFile必须和name的参数的值保持一致
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会被删除！！！！！！！！！！

        //原始文件名
        String originalFilename = file.getOriginalFilename();//文件名.jpg注意这个后缀是需要保留的
        String suffix=originalFilename.substring(originalFilename.indexOf("."));//文件的后缀名

        //使用UUID重新生成文件名，防止文件名称重复造成的文件覆盖
        String fileName= UUID.randomUUID().toString()+suffix;//文件名+后缀


        try {
            //将临时文件转存到指定位置
            /**
             * 下面的这一段代码是相当关键的，相当于上传资源到对应的服务器上面去
             */
            file.transferTo(new File(basePath+fileName));
        }catch (IOException e){
            e.printStackTrace();
        }

        return R.success(fileName);
    }


    /**
     * 文件的下载
     * 电脑这个时候相当于一个服务器，资源全部被上传到服务器上面，下载的时候就是浏览器从服务器加载资源然后去显示
     * 具体的体现：图片资源的额显示和回显
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream=new FileInputStream(new File(basePath+name));
            //这里设置的是一个输入流,主要的作用是读的作用，这时候相关的byte字节已经充满了fileInputStream的管道

            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream=response.getOutputStream();
            //这里设置的是一个输出流，主要的作用是写的作用


            //设置的是返回的类型是什么,这里表示的是返回的是图片文件
            response.setContentType("image/jpeg");


            /**
             * 从输入流中读取文件到浏览器中的操作
             */
            int len=0;
            byte[] bytes=new byte[1024];
            while((len=fileInputStream.read(bytes))!=-1) {
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            outputStream.close();fileInputStream.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
