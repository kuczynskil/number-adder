package com.empik.numberadder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@SpringBootApplication
@EnableEurekaClient
public class NumberAdderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberAdderApplication.class, args);
    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.empik"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Number-sum API",
                "API of a microservice, which sums numbers in a String",
                "1.0",
                "Free to use",
                new Contact("Łukasz Kuczyński", "https://github.com/kuczynskil",
                        "l.kuczynski95@gmail.com"),
                "API license",
                "https://github.com/kuczynskil",
                Collections.emptyList()
        );
    }
}
