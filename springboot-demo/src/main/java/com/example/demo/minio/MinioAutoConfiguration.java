package com.example.demo.minio;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.AllArgsConstructor;

/**
 * @author xiexingxing
 * @Created by 2020-03-04 21:46.
 */
@AllArgsConstructor
@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {

    private final MinioProperties properties;

    @Bean
    @ConditionalOnProperty(name = "minio.url")
    MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(
                properties.getUrl(),
                properties.getAccessKey(),
                properties.getSecretKey()
        );
    }
}