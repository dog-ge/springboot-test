package org.example.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import java.util.UUID;

@RestController
public class uploadController {
     @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd/");
    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
    @GetMapping("/s")
    public ModelAndView indexs(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index_s");
        return mv;
    }

    @Value("${file.upload-dir}")
    private String uploadDir; //文件存放目录

//    上传单文件
    @PostMapping("/upload")
    public String upload(MultipartFile uploadFile, HttpServletRequest req){
//        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/"); //运行目录下的的/uploadFile文件夹
//        File folder = new File(realPath+format);

        String format = sdf.format(new Date());
        File folder = new File(uploadDir, format);

        if (!folder.isDirectory()){
            folder.mkdirs();
        }
        String oldName = uploadFile.getOriginalFilename(); //上传文件的原始文件名
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try {
            uploadFile.transferTo(new File(folder,newName));
            String filePath = req.getScheme() +"://"+req.getServerName()+":"+req.getServerPort()+"/uploadFile/"+format+newName;
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

//上传多文件
    @PostMapping("/upload_files")
    public ArrayList<String> upload_files(MultipartFile[] uploadFiles, HttpServletRequest req){
        ArrayList  filePaths = new ArrayList<>();
        for (MultipartFile uploadFile :uploadFiles){
            String format = sdf.format(new Date());
            File folder = new File(uploadDir, format);
            if (!folder.isDirectory()){
                folder.mkdirs();
            }
            String oldName = uploadFile.getOriginalFilename(); //上传文件的原始文件名
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
            try {
                uploadFile.transferTo(new File(folder,newName));
                String filePath = req.getScheme() +"://"+req.getServerName()+":"+req.getServerPort()+"/uploadFile/"+format+newName;
               filePaths.add(filePath);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return filePaths;

    }
}
