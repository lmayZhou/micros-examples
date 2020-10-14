package com.lmaye.ms.service.file.controller;

import com.lmaye.ms.core.context.ResponseResult;
import com.lmaye.ms.core.context.ResultCode;
import com.lmaye.ms.core.exception.ServiceException;
import com.lmaye.ms.starter.minio.service.IMinIoFileStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * -- 文件存储 Controller
 *
 * @author lmay.Zhou
 * @qq 379839355
 * @email lmay@lmaye.com
 * @date 2020/10/14 22:28 星期三
 * @since JDK1.8
 */
@RestController
@RequestMapping("/fileStore")
@Api(tags = "文件存储相关接口")
public class FileStoreController {
    /**
     * MinIo File Store Service
     */
    @Autowired
    private IMinIoFileStoreService minIoFileStoreService;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return ResponseResult<String>
     */
    @PostMapping("/uploadFile")
    @ApiOperation(value = "文件上传", notes = "文件上传", response = ResponseResult.class)
    public ResponseResult<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseResult.success(minIoFileStoreService.saveStream(file.getInputStream(),
                    file.getOriginalFilename(), file.getContentType()));
        } catch (IOException e) {
            throw new ServiceException(ResultCode.FAILURE);
        }
    }

    /**
     * 获取签名地址
     *
     * @param fileName 文件名称
     * @return ResponseResult<String>
     */
    @PostMapping("/preSignedUrl/{fileName}")
    @ApiOperation(value = "获取签名地址", notes = "获取签名地址", response = ResponseResult.class)
    public ResponseResult<String> preSignedUrl(@PathVariable String fileName) {
        return ResponseResult.success(minIoFileStoreService.preSignedUrl(fileName, 1, TimeUnit.DAYS));
    }
}
