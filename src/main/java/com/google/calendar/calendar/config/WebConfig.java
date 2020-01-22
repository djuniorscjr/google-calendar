package com.google.calendar.calendar.config;

import com.google.calendar.calendar.handlers.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.validation.Validator;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Autowired
    private Validator defaultValidator;

    public Validator getDefaultValidator() {
        return defaultValidator;
    }

    @Bean
    public RouterFunction<ServerResponse> routes(UserHandler userHandler) {
        return route(POST("/user"), userHandler::createPerson);
    }
}
