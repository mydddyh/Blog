package com.mydddyh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mydddyh.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("DYH", "http://duyuheng.cn", "610377357@qq.com");
        return new ApiInfoBuilder()
                .title("Blog后台")
                .description("Blog后台的接口文档描述")
                .contact(contact)   // 联系方式
                .version("1.0")  // 版本
                .build();
    }
}
