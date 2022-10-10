package com.limengxiang.service;

import com.limengxiang.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);

    ResponseResult deleteImg(String filePath);
}
