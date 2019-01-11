package com.jeesite.modules.js.web;

import com.jeesite.modules.common.utils.ImageUtil;
import com.jeesite.modules.js.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("${adminPath}/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // 上传图片到本地
    @PostMapping("/images")
    public String upload(@RequestParam("image") MultipartFile image) {
        return imageService.upload(image);
    }

    // 查看本地图片
    @GetMapping("/images")
    public void getImage(
            @RequestParam("imageName") String imageName,
            HttpServletResponse response) throws Exception {
        response.setContentType("image/jpg");
        StreamUtils.copy(new FileInputStream(ImageUtil.IMAGE_DIR + imageName), response.getOutputStream());
    }
}
