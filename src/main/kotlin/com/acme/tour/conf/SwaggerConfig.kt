package com.acme.tour.conf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun lojaApi(): Docket =
         Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.acme.tour"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())


    private fun metaData(): ApiInfo =
        ApiInfoBuilder()
            .title("API de Loja ACME")
            .description("API que possui todos os metodos da loja")
            .version("1.0.0")
            .build()
}