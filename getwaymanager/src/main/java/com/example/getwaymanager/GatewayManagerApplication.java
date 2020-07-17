
package com.example.getwaymanager;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lengleng
 * @date 2018年06月21日
 * 网关应用
 */
@SpringBootApplication
@MapperScan("com.example.getwaymanager.mapper")
public class GatewayManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayManagerApplication.class, args);
	}
}
