package com.sxt.sys.controller;


import cn.hutool.core.date.DateUtil;
import com.sxt.sys.common.AppFileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {
    @RequestMapping("uploadFile")
//    构建上传系统
    public Map<String,Object> uploadFile(MultipartFile mf){
        String oldName=mf.getOriginalFilename();
        String newName= AppFileUtils.createNewFileName(oldName);
//        创造新名字
//        构建文件夹名
        String dirName= DateUtil.format( new Date(), "yyyy-mm-dd");
        File dirFile=new File(AppFileUtils.UPLOAD_PATH, dirName);
        if (!dirFile.exists()){
            dirFile.mkdir();
//            如果不存在就创建
        }
//        构建图存放的文件
        File file=new File(dirFile,newName+"_temp");
        try {
            mf.transferTo(file);
//            把图片写入到文件里
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("path", dirName+"/"+newName+"_temp");
        return map;


    }

//    图片显示
    @RequestMapping("showImageByPath")
    public ResponseEntity<Object> showImageByPath(String path){
        return AppFileUtils.createResponseEntity(path);
    }

}
