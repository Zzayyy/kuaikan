package cn.kshost.fastview.backend.controller;

import cn.kshost.fastview.backend.pojo.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @PostMapping("/video")
    public Result uploadVideo(@RequestParam("file") MultipartFile file) {



        return null;

    }
}
