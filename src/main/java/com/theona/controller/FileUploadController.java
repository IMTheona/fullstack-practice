package com.theona.controller;

import com.theona.pojo.Result;
import com.theona.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        // 把文件存储到本地磁盘
        String originalFilename = file.getOriginalFilename();

        // 保证文件名字唯一
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // file.transferTo(new File("D:\\2026SummerPractice\\files\\" + filename));
        String url = AliOssUtil.uploadFile(filename,file.getInputStream());

        return Result.success(url);
    }
}
