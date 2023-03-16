package com.mydddyh.service.impl;

import com.google.gson.Gson;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.service.UploadService;
import com.mydddyh.utils.ServiceUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Data
@Service
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    private String domain;          // OSS域名
    private String bucket;          // OSS项目名
    private String accessKey;       // OSS公钥
    private String secretKey;       // OSS私钥
    private Boolean inDatePath;     // 文件是否按天存放

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        // 校验文件类型
        String originalFilename = img.getOriginalFilename();
        if(!originalFilename.endsWith(SystemConstants.PICTURE_JPG_FILE_FORMAT)
                && !originalFilename.endsWith(SystemConstants.PICTURE_JPEG_FILE_FORMAT)
                && !originalFilename.endsWith(SystemConstants.PICTURE_PNG_FILE_FORMAT)){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        // 生成文件名并上传, 如[2099/12/31/]abcd.png
        String filePath = ServiceUtils.generateFilePath(originalFilename, inDatePath);
        String url = uploadOss(img,filePath);
        return ResponseResult.okResult(url);
    }

    private String uploadOss(MultipartFile imgFile, String filePath){
        // 参考 https://developer.qiniu.com/kodo/1239/java
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());

        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
                return domain + key;
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
