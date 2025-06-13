package cn.kshost.fastview.backend.service.impl;

import cn.kshost.fastview.backend.service.FileUploadService;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileUpLoadServiceImpl implements FileUploadService {
    @Value("${fastview.minio.endpoint}")
    private String endpoint;

    @Value("${fastview.minio.access-key}")
    private String accessKey;

    @Value("${fastview.minio.secret-key}")
    private String secretKey;

    @Value("${fastview.minio.bucket}")
    private String bucketName;

    private MinioClient minioClient;


    @PostConstruct
    public void init() {
        // 初始化MinIO客户端
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @SneakyThrows
    @Override
    public String upLoadvideo(MultipartFile file) {
        // 1. 验证文件
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }

        // 2. 验证文件类型
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !isVideoFile(originalFilename)) {
            throw new RuntimeException("请上传视频文件");
        }

        // 3. 生成唯一文件名
        String fileExtension = getFileExtension(originalFilename);
        String fileName = generateUniqueFileName() + fileExtension;

        // 4. 创建bucket（如果不存在）
        createBucketIfNotExists();

        // 5. 上传文件到MinIO
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }
        // 6. 生成访问URL
        return generateVideoUrl(fileName);
    }


    /**
     * 验证是否为视频文件
     */
    private boolean isVideoFile(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return extension.matches("\\.(mp4|avi|mov|wmv|flv|webm|mkv|m4v|3gp)$");
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * 生成唯一文件名
     */
    private String generateUniqueFileName() {
        return "video_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 创建bucket（如果不存在）
     */
    private void createBucketIfNotExists() throws Exception {
        boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
        );

        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build()
            );

            // 设置bucket为公共读取（这样前端可以直接访问视频）
            setBucketPolicy();
        }
    }

    /**
     * 设置bucket策略为公共读取
     */
    private void setBucketPolicy() throws Exception {
        String policy = "{\n" +
                "  \"Version\": \"2012-10-17\",\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Principal\": {\n" +
                "        \"AWS\": [\"*\"]\n" +
                "      },\n" +
                "      \"Action\": [\"s3:GetObject\"],\n" +
                "      \"Resource\": [\"arn:aws:s3:::" + bucketName + "/*\"]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                        .bucket(bucketName)
                        .config(policy)
                        .build()
        );
    }

    /**
     * 生成视频访问URL
     */
    private String generateVideoUrl(String fileName) {
        // 如果bucket设置为公共访问，可以直接返回公共URL
        return endpoint + "/" + bucketName + "/" + fileName;
    }
}

