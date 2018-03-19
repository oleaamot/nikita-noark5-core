package nikita.webapp.spring;


import io.swagger.annotations.Api;
import nikita.webapp.config.WebappProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static nikita.common.config.Constants.SPRING_PROFILE_SWAGGER;


@Configuration
@EnableSwagger2
@Profile(SPRING_PROFILE_SWAGGER)
@EnableConfigurationProperties(WebappProperties.class)
public class SwaggerConfig {

    private static final String DEFAULT_INCLUDE_PATTERN = "/.*";
    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);
    @Autowired
    private WebappProperties webappProperties;


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any()).build().pathMapping("/")
                .apiInfo(apiInfo()).useDefaultResponseMessages(false);
    }

    @Bean
    ApiInfo apiInfo() {
        final ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("BlazeMeter Spring Boot API").version("1.0").license("(C) Copyright BlazeMeter")
                .description("List of all endpoints used in API");
        return builder.build();
    }

}
