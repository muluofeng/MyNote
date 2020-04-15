package com.example.demo;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义逻辑返回需要导入的组件
 *
 * @author xiexingxing
 * @Created by 2019-09-10 17:09.
 */
public class MyImportSelector implements ImportSelector {

    //返回值就是导入到容器中的组件全类名
    //  AnnotationMetadata 当前标注@Import 注解的类的所有的注解信息
    //                     现在也就是 MyImportSelector使用的类（DemoApplication）上面的注解信息
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //返回类的全类名
        return new String[]{"com.example.demo.proxy.Blue"};
    }
}
