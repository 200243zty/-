package com.xs.controller;

import com.xs.common.Result;
import com.xs.config.MinioConfig;
import com.xs.enums.FilePathEnum;
import com.xs.strategy.context.UploadStrategyContext;
import com.xs.util.MinioUtils;
import com.xs.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class UploadController {

    @Resource
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

    /**
     * 上传歌手头像
     */
    @PostMapping("/upload")
    public R<String> upload(@RequestParam("pic") MultipartFile file) {
//        return R.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath()));
            String fileName = file.getOriginalFilename();
            String objectKey ="/avatar/"+ System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            String contentType = file.getContentType();
            minioUtils.uploadFile(minioConfig.getBucketName(), file, objectKey, contentType);
            String path = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + objectKey;
            return R.ok(path);
    }

    /**
     * 上传歌曲mp3
     */
    @PostMapping("/uploadAudio")
    public R<String> uploadAudio(@RequestParam("mp3") MultipartFile file) {
//        return R.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.VOICE.getPath()));
        String fileName = file.getOriginalFilename();
        String objectKey ="/voice/"+ System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
        String contentType = file.getContentType();
        minioUtils.uploadFile(minioConfig.getBucketName(), file, objectKey, contentType);
        String path = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + objectKey;
        return R.ok(path);
    }

    /**
     * 上传视频
     */
    @PostMapping("/uploadVideo")
    public R<String> uploadVideo(@RequestParam("video") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String objectKey ="/video/"+ System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
        String contentType = file.getContentType();
        minioUtils.uploadFile(minioConfig.getBucketName(), file, objectKey, contentType);
        String path = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + objectKey;
        return R.ok(path);
    }

    /**
     * 上传用户背景图片
     */






    /**
     * 删除
     *
     * @param fileName
     */
    @GetMapping("/")
    public void delete(@RequestParam("fileName") String fileName) {
        minioUtils.removeFile(minioConfig.getBucketName(), fileName);
    }

    /**
     * 获取文件外链
     *
     * @param fileName
     * @return
     */
    @GetMapping("/url")
    public String getPresignedObjectUrl(@RequestParam("fileName") String fileName) {
        return minioUtils.getPresignedObjectUrl(minioConfig.getBucketName(), fileName);
    }



}
