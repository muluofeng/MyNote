package com.example.demo.minio;

import com.google.api.client.util.IOUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiexingxing
 * @Created by 2020-03-04 21:48.
 */
@Slf4j
@Component
@AllArgsConstructor
public class MinioStorageService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    //上传文件到minio服务
    public String upload(MultipartFile file) {
        try {
            InputStream is = file.getInputStream(); //得到文件流
            String fileName = file.getOriginalFilename(); //文件名
            String contentType = file.getContentType();  //类型
            minioClient.putObject(minioProperties.getBucketName(), fileName, is, contentType); //把文件放置Minio桶(文件夹)
            return "上传成功";
        } catch (Exception e) {
            return "上传失败";
        }
    }

    //下载minio服务的文件
    @GetMapping("download")
    public String download(HttpServletResponse response) {
        try {
            InputStream fileInputStream = minioClient.getObject(minioProperties.getBucketName(), "test.jpg");
            response.setHeader("Content-Disposition", "attachment;filename=" + "test.jpg");
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
            return "下载完成";
        } catch (Exception e) {
            return "下载失败";
        }
    }

    //获取minio文件的下载地址
    @GetMapping("url")
    public String getUrl() {
        try {
            String url = minioClient.presignedGetObject(minioProperties.getBucketName(), "test.jpg");
            return url;
        } catch (Exception e) {
            return "获取失败";
        }
    }
}
