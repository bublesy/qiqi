package com.qiqi.admin.order.api;

import com.qiqi.admin.order.util.FileDfsUtil;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hc
 * @since 2020-09-19
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileDfsUtil fileDfsUtil;

    @ApiOperation(value="上传文件", notes="测试FastDFS文件上传")
    @RequestMapping(value = "/upload",headers="content-type=multipart/form-data", method = RequestMethod.POST)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file){
//        String filePath =  File.separator+"www"+File.separator+"wwwroot"+File.separator+"img.qxjkkj.com"+File.separator; // 上传后的路径
           String filePath = this.getClass().getResource("/").getPath() +  "static"; // 上传后的路径
        Date date = new Date();
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String savePath="/upload/"+year.format(date)+"/"+month.format(date)+"/"+day.format(date)+"/"+ file.getName()+".jpg";
        File dest = new File(filePath+savePath);
        //判断文件保存是否存在
        if (!dest.getParentFile().exists()) {
            //创建文件
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            while (dest.length()>300*1024) {
                Thumbnails.of(dest).scale(0.8f)      //压缩大小
                        .outputQuality(0.75f)         //压缩质量
                        .toFile(dest);
            }
//            savePath="http://img.qxjkkj.com/"+savePath;
//            return DtoUtil.returnSuccess( "图片上传成功", (savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(filePath);
    }

    @RequestMapping(value = "/deleteByPath", method = RequestMethod.GET)
    public ResponseEntity<String> deleteByPath (){
        String filePathName = "group1/M00/00/00/wKhIgl0n4AKABxQEABhlMYw_3Lo825.png" ;
        fileDfsUtil.deleteFile(filePathName);
        return ResponseEntity.ok("SUCCESS") ;
    }
}
