package gzm.demo.springboot.boot_feature;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// MyBatis 支持
@MapperScan("gzm.demo.springboot.boot_feature.dao")
//事务支持
@EnableTransactionManagement
// filter 支持
@ServletComponentScan(basePackages = "gzm.demo.springboot.boot_feature.web")
//@ComponentScan("gzm.demo.springboot.boot_feature.aspect.*")
// 注册eureka服务
@EnableDiscoveryClient
@SpringBootApplication
public class MainApplicion extends SpringBootServletInitializer{
	
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(MainApplicion.class);
    }

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainApplicion.class, args);
	}

}