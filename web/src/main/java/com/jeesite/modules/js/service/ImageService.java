package com.jeesite.modules.js.service;

import com.jeesite.modules.common.utils.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {

    public String upload(MultipartFile image) {
        int dotPos = image.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return "非法文件!";
        }
        String imageExt = image.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!ImageUtil.isImageAllowed(imageExt)) {
            return "不支持图片格式";
        }
        String imageName = UUID.randomUUID().toString().replace("-", "") + "." + imageExt;
        try {
            Files.copy(image.getInputStream(), new File(ImageUtil.IMAGE_DIR + imageName).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return ImageUtil.IMAGE_DOMAIN + "images?imageName=" + imageName;
        } catch (IOException e) {
            return "上传失败";
        }
    }
}