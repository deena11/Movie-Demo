package com.example.moviesearch.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author M1053559
 * @description configuration for swagger 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	@Value("${token.url}")
	private String accessTokenUri;
	
	private static final String READ="read";
	private static final String WRITE="write";
	private static final String TRUST="trust";
	

	@Bean
	public Docket searchApi() {

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.example.moviesearch.controller"))
				.paths(PathSelectors.any()).build().securityContexts(Collections.singletonList(securityContext()))
				.securitySchemes(Arrays.asList(securitySchema())).apiInfo(apiInfo());

	}


	private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = newArrayList();
		authorizationScopeList.add(new AuthorizationScope(READ, "read all"));
		authorizationScopeList.add(new AuthorizationScope(WRITE, "access all"));

		List<GrantType> grantTypes = newArrayList();
		GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
		grantTypes.add(passwordCredentialsGrant);

		return new OAuth("oauth2", authorizationScopeList, grantTypes);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
		authorizationScopes[0] = new AuthorizationScope(READ, "read all");
		authorizationScopes[1] = new AuthorizationScope(TRUST, "trust all");
		authorizationScopes[2] = new AuthorizationScope(WRITE, "write all");

		return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
	}

	@SuppressWarnings("deprecation")
	@Bean
	public SecurityConfiguration security() {
		return new SecurityConfiguration(clientId, clientSecret, "", "", "Bearer access token", ApiKeyVehicle.HEADER,
				HttpHeaders.AUTHORIZATION, "");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Search service").description("").license("Personal Learning").version("1.0.0")
				.build();
	}

}
