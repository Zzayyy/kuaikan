package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import cn.kshost.fastview.backend.pojo.result.Result;
import cn.kshost.fastview.backend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/video")
    public Result<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            String videoUrl = fileUploadService.upLoadvideo(file);
            return Result.success(FastViewEnum.UPLOAD_SUCCESS ,videoUrl);
        } catch (Exception e) {
            return Result.error(FastViewEnum.UPLOAD_ERROR,e.getMessage());
        }
    }
}
