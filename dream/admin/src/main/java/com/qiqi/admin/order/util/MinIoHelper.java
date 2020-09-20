package com.qiqi.admin.order.util;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhao
 */
@Component
public class MinIoHelper {

    @Value(value = "${minio.host}")
    private String host;

    @Value(value = "${minio.access-key}")
    private String accessKey;

    @Value(value = "${minio.secret-key}")
    private String secretKey;

    public String putObject(MultipartFile multipartFile, String bucket) throws
            IOException, InvalidKeyException, ErrorResponseException, IllegalArgumentException,
            InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException,
            NoSuchAlgorithmException, XmlParserException, RegionConflictException, ServerException {

        MinioClient minioClient = MinioClient.builder()
                .endpoint(this.host)
                .credentials(this.accessKey, this.secretKey)
                .build();

        // bucket 不存在，创建
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {

            // 上传文件的名称
            String fileName = multipartFile.getOriginalFilename();
            assert fileName != null;

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .stream(inputStream, -1, 5242880L)
                    .contentType(multipartFile.getContentType())
                    .build());
            // 返回访问路径
            String encode = UriUtils.encode(fileName, StandardCharsets.UTF_8);
            return encode;
        }
    }

    public String removeObject(String bucket, String fileName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(this.host)
                .credentials(this.accessKey, this.secretKey)
                .build();
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).object(fileName).build());
        return "删除成功";
    }
}