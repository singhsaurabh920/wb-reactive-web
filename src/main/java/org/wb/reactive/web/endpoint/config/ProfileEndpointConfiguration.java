package org.wb.reactive.web.endpoint.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.wb.reactive.web.endpoint.config.CaseInsensitiveRequestPredicate;
import org.wb.reactive.web.endpoint.handler.ProfileHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProfileEndpointConfiguration {
    private static final Logger logger = LogManager.getLogger(ProfileEndpointConfiguration.class);

    @Bean
    @Profile("functional")
    RouterFunction<ServerResponse> routes(ProfileHandler handler) {
        return route(i(GET("/profiles")), handler::all)
            .andRoute(i(GET("/profiles/{id}")), handler::getById)
            .andRoute(i(DELETE("/profiles/{id}")), handler::deleteById)
            .andRoute(i(POST("/profiles")), handler::create)
            .andRoute(i(PUT("/profiles/{id}")), handler::updateById);
    }

    private static RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }
}