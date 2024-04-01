package co.com.devsu.bank.account.config;

import co.com.devsu.bank.account.controllers.AccountRestController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * @author andresfelipegarciaduran
 * @version 0.0.1
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression(value = "${swagger.enable-ui:false}")
public class SwaggerConfig {

    private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .produces(DEFAULT_PRODUCES_CONSUMES)
                .consumes(DEFAULT_PRODUCES_CONSUMES)
                .select()
                .apis(RequestHandlerSelectors.basePackage(AccountRestController.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("API-REST / MS ACCOUNT",
                "Service contract - Microservices to resolve requirements for entities.",
                "1.0",
                "Terms of service",
                new Contact("Andres Duran", "null01", "andresduran0205@gmail.com"),
                "",
                "",
                new ArrayList<>());
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
    }


}
