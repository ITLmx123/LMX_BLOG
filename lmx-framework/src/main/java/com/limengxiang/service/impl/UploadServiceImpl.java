package com.limengxiang.service.impl;

import com.google.gson.Gson;
import com.limengxiang.domain.ResponseResult;
import com.limengxiang.enums.ResponseCodeEnum;
import com.limengxiang.exception.SystemException;
import com.limengxiang.service.UploadService;
import com.limengxiang.utils.PathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${qiNiuYun.accessKey}")
    private String accessKeys;
    @Value("${qiNiuYun.secretKey}")
    private String secretKeys;
    @Value("${qiNiuYun.bucket}")
    private String buckets;


    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //获取文件原始文件名
        String originalFilename = img.getOriginalFilename();
        if(originalFilename==null){
            throw new SystemException(ResponseCodeEnum.FILE_NAME_NULL);
        }
        if (!originalFilename.endsWith(".png")&&!originalFilename.endsWith(".jpg")&&!originalFilename.endsWith(".jpeg")){
            throw new SystemException(ResponseCodeEnum.FILE_TYPE_ERROR);
        }
        String filePath= PathUtils.generateFilePath(originalFilename);
        String url = uploadQiNiuYun(img, filePath);
        return ResponseResult.okResult(url);
    }

    @Override
    public ResponseResult deleteImg(String url) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        String key = url.substring(49,url.length()-2);
        System.out.println(url);
        System.out.println(key);
        Auth auth = Auth.create(accessKeys, secretKeys);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(buckets, key);
            return ResponseResult.okResult();
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
        return ResponseResult.errorResult(ResponseCodeEnum.FILE_DELETE_ERROR);
    }

    private String uploadQiNiuYun(MultipartFile img,String filePath){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        System.out.println(accessKeys);
        String accessKey = accessKeys;
        String secretKey = secretKeys;
        String bucket = buckets;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://ri6tpk8ym.hd-bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return null;
    }
}
