package cn.kshost.fastview.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String upLoadvideo(MultipartFile file);
}
