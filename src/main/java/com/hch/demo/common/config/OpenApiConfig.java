package com.hch.demo.common.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion,
       @Value("${demo.url}") String url, @Value("${spring.profiles.active}") String active ) {

        // http://localhost:18180/hch/swagger-ui/index.html

        Info info = new Info().title("Demo API - " + active).version(appVersion)
                .description("Spring Boot를 이용한 Demo 웹 애플리케이션 API입니다.")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("hch").url("https://www.naver.com/").email("dohauzi@gmail.com"))
                .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

        List<Server> servers = Arrays.asList(new Server().url(url).description("demo (" + active + ")"));   // 서버정보
        return new OpenAPI()
                .components(new Components())
                .info(info)
                .servers(servers);
    }

}
