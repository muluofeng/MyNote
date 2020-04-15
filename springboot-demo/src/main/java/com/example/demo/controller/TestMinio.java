package com.example.demo.controller;

import com.example.demo.minio.MinioStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiexingxing
 * @Created by 2020-03-04 21:52.
 */
@RestController
@RequestMapping("/minio")
public class TestMinio {

    @Autowired
    MinioStorageService minioStorageService;

    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String path = minioStorageService.upload(file);
        return path;
    }

}