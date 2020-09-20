package com.qiqi.admin.order.api;

import cn.hutool.core.io.FileTypeUtil;
import com.qiqi.admin.order.util.MinIoHelper;
import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * @author QiQiDream
 * @since 2020-07-03 14:39
 */
@Api(tags = "文件存储相关接口")
@RestController
@RequestMapping("/oss")
public class OssController {
    private static final List<String> IMAGE_TYPE =  Arrays.asList("jpg","png","gif");
    private static final List<String> VIDEO_TYPE =  Arrays.asList("mp4","mpg","wmv","flv");
    /**
     * 注意 xlsx、docx本质上是各种XML打包为zip的结果，因此会被识别为zip格式。
     */
    private static final List<String> DOC_TYPE =  Arrays.asList("xls","rar","pdf","zip");

    @Resource
    MinIoHelper minioHelper;

    @Value(value = "${minio.imageBucket}")
    private String imageBucket;

    @Value(value = "${minio.videoBucket}")
    private String videoBucket;

    @Value(value = "${minio.docBucket}")
    private String docBucket;

    @ApiOperation(value = "上传文件（自动识别图片/视频/文档）")
    @PostMapping("")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException, InvalidResponseException, RegionConflictException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        String type = FileTypeUtil.getType(multipartFile.getInputStream());
        if (IMAGE_TYPE.contains(type)){
            return minioHelper.putObject(multipartFile,imageBucket);
        } else if (VIDEO_TYPE.contains(type)){
            return minioHelper.putObject(multipartFile,videoBucket);
        } else if (DOC_TYPE.contains(type)){
            return minioHelper.putObject(multipartFile,docBucket);
        } else {
            throw new RuntimeException("文件格式不支持");
        }
    }


    @ApiOperation(value = "删除图片")
    @DeleteMapping("/image/{name}")
    public String deleteImage(@PathVariable String name) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        return minioHelper.removeObject(imageBucket,name);
    }


    @ApiOperation(value = "删除视频")
    @DeleteMapping("/video/{name}")
    public String deleteVideo(@PathVariable String name) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        return minioHelper.removeObject(videoBucket,name);
    }


    @ApiOperation(value = "删除视频")
    @DeleteMapping("/doc/{name}")
    public String deleteDoc(@PathVariable String name) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        return minioHelper.removeObject(docBucket,name);
    }
}
