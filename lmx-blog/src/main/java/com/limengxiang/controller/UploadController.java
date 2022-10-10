package com.limengxiang.controller;

import com.limengxiang.annotation.SystemLog;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping()
public class UploadController {
    @Autowired
    private UploadService uploadService;

   @SystemLog(businessName = "头像上传接口")
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }

    @SystemLog(businessName = "头像删除接口")
    @DeleteMapping("/deleteImg")
    public ResponseResult deleteImg(@RequestBody String filePath){
        return uploadService.deleteImg(filePath);
    }
}
