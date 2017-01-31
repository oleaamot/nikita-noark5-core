package no.arkivlab.hioa.nikita.webapp.spring;


import nikita.config.Constants;
import no.arkivlab.hioa.nikita.webapp.config.WebappProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;


@Configuration
@EnableSwagger2
@Profile({ Constants.SPRING_PROFILE_DEVELOPMENT, Constants.SPRING_PROFILE_DEMO})
@EnableConfigurationProperties(WebappProperties.class)
public class SwaggerConfig {

    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);
    private static final String DEFAULT_INCLUDE_PATTERN = "/.*";

    @Autowired
    private WebappProperties webappProperties;

    @Bean
    public Docket swaggerDocket() { // @formatter:off

        logger.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                webappProperties.getSwagger().getContactName(),
                webappProperties.getSwagger().getContactUrl(),
                webappProperties.getSwagger().getContactEmail());

        ApiInfo apiInfo = new ApiInfo(
                webappProperties.getSwagger().getTitle(),
                webappProperties.getSwagger().getDescription(),
                webappProperties.getSwagger().getVersion(),
                webappProperties.getSwagger().getTermsOfServiceUrl(),
                contact,
                webappProperties.getSwagger().getLicense(),
                webappProperties.getSwagger().getLicenseUrl());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                .build()
                .pathMapping(Constants.HATEOAS_API_PATH)
                .genericModelSubstitutes(ResponseEntity.class);

        watch.stop();
        logger.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
        // @formatter:on
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfiguration.DEFAULT;
    }

}
