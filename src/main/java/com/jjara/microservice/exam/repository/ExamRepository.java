package com.jjara.microservice.exam.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jjara.microservice.exam.pojo.Exam;

import reactor.core.publisher.Flux;

public interface ExamRepository extends ReactiveMongoRepository<Exam, Long> {
		
	@Query("{ id: { $exists: true }}")
	public Flux<Exam> findAll(Pageable page);

	
}