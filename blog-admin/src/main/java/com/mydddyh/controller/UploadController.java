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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Api(tags = "后台上传文件")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @PostMapping("/upload")
    @SystemLog(businessName = "上传图像文件", requestArgs = "图像文件")
    @ApiOperation(value = "上传图像文件")
    @ApiImplicitParam(name = "img", value = "需要上传的图像文件")
    public ResponseResult uploadImg(MultipartFile img){
        if (img == null) {
            throw new SystemException(AppHttpCodeEnum.FILE_UPLOAD_ERROR);
        }
        try {
            return uploadService.uploadImg(img);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(AppHttpCodeEnum.FILE_UPLOAD_ERROR);
        }
    }
}
