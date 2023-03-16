package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "前台上传文件")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @SystemLog(businessName = "上传头像文件", requestArgs = "头像文件")
    @ApiOperation(value = "上传头像")
    @ApiImplicitParam(name = "img", value = "需要上传的头像文件")
    public ResponseResult uploadImg(MultipartFile img){
        if (img == null) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        return uploadService.uploadImg(img);
    }
}
