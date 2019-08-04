package com.jjara.microservice.exam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jjara.microservice.exam.handler.ExamHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
class ProfileEndpointConfiguration {

	@Bean
	protected RouterFunction<ServerResponse> routes(final ExamHandler handler) {
		return route(GET("/exam/{page}/{size}/{tag}"), handler::all)
				.andRoute(GET("/exam/{id}"), handler::getById)
				.andRoute(DELETE("/exam/{id}"), handler::deleteById)
				.andRoute(POST("/exam"), handler::create)
				.andRoute(PUT("/exam/{id}"), handler::updateById);
	}

}