package com.limengxiang;


import com.google.gson.Gson;
import com.limengxiang.service.ArticleService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
class LmxBlogApplicationTests {
    @Autowired
    private ArticleService articleService;
    @Value("${qiNiuYun.accessKey}")
    private String accessKeys;
    @Value("${qiNiuYun.secretKey}")
    private String secretKeys;
    @Value("${qiNiuYun.bucket}")
    private String buckets;

    @Test
    void qiNiuYunText() {
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
        String key = null;
        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            InputStream inputStream = new FileInputStream(new File("D:\\WorkSpace\\ideaBackGround.jpg"));
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
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
    }

    @Test
    void deleteQiNiuYun() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        String url = "http://ri6tpk8ym.hd-bkt.clouddn.com/2022/09/14/0d2e41cc75a149488d33a76d505c6126.png";
        String key = url.substring(36);
        System.out.println(key);
        Auth auth = Auth.create(accessKeys, secretKeys);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(buckets, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }




    @Test
    void random2() {
        String result = new String();
        for (int i=0;i<6;i++) {
            int random = (int) (Math.random() * 9 + 1);
            result = result + random;
        }
        System.out.println(result);
    }

}

