package com.example.demo;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author xiexingxing
 * @Created by 2019-09-10 15:46.
 */
public class MyFilterType implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //当前正在扫面的类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //当前类的资源（类的路径）
        Resource resource = metadataReader.getResource();
        //获取类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

//        System.out.println(classMetadata.getClassName());

        return false;  //返回true表示注册到容器
    }
}
