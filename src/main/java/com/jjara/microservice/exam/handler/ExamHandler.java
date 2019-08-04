package com.jjara.microservice.exam.handler;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jjara.microservice.exam.ResponseHandler;
import com.jjara.microservice.exam.pojo.Exam;
import com.jjara.microservice.exam.service.ExamService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ExamHandler {

	private final ExamService service;

	public ExamHandler(ExamService profileService) {
		this.service = profileService;
	}

	public Mono<ServerResponse> getById(ServerRequest serverRequest) {		
		return ResponseHandler.okNoContent(service.get(id(serverRequest)));
	}

	public Mono<ServerResponse> all(ServerRequest r) {
		return defaultReadResponseList(service.findAll(page(r), size(r), tag(r)));
	}

	public Mono<ServerResponse> deleteById(ServerRequest r) {
		return ResponseHandler.okNoContent(service.delete(id(r)));
	}

	public Mono<ServerResponse> updateById(ServerRequest serverRequest) {
		final Flux<Exam> id = serverRequest.bodyToFlux(Exam.class).flatMap(p -> 
			this.service.update(id(serverRequest), 
					p.getTitle(), p.getCode(), p.getQuestions()));
		return defaultReadResponse(id);
	}

	public Mono<ServerResponse> create(ServerRequest request) {
		Mono<Exam> flux = request.bodyToMono(Exam.class)
				.flatMap(data -> this.service.create(data.getTitle(), data.getCode()));
		return defaultReadResponse(flux);
	}

	private static Mono<ServerResponse> defaultReadResponse(Publisher<Exam> profiles) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(profiles, Exam.class);
	}
	
	private static Mono<ServerResponse> defaultReadResponseList(Publisher<Exam> profiles) {
		Flux.from(profiles).flatMap(ResponseHandler::ok).switchIfEmpty(ResponseHandler.noContent());
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(profiles, Exam.class);
	}

	private static Long id(ServerRequest r) {
		return Long.valueOf(r.pathVariable("id"));
	}
	
	private static Integer page(ServerRequest r) {
		return serverRequestProperty(r, "page");
	}
	
	private static Integer size(ServerRequest r) {
		return serverRequestProperty(r, "size");
	}
	
	private static Integer tag(ServerRequest r) {
		return serverRequestProperty(r, "tag");
	}
	
	
	private static Integer serverRequestProperty(final ServerRequest r, final String property) {
		Integer value = null;
		try {
			value = Integer.valueOf(r.pathVariable(property));
		} catch (IllegalArgumentException e) {
			value = 0;
		}
		return value;
	}
	
}