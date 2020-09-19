package com.qiqi.admin.order.util;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author hc
 * @since 2020-09-18
 */
@Component
public class FileDfsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDfsUtil.class);

    @Resource
    private FastFileStorageClient storageClient;

    public String upload(MultipartFile multipartFile) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                multipartFile.getInputStream(),
                multipartFile.getSize(),originalFileName,null);
        return storePath.getFullPath();
    }

    public void deleteFile(String fileUrl){
        if(StringUtils.isEmpty(fileUrl)){
            LOGGER.info("文件路径为空");
        }
        try{
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(),storePath.getPath());
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
    }
}
