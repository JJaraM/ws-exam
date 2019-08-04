package com.jjara.microservice.exam.service;

import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.jjara.microservice.exam.pojo.Exam;
import com.jjara.microservice.exam.pojo.Question;
import com.jjara.microservice.exam.repository.ExamRepository;
import com.jjara.microservice.exam.repository.SequenceRepository;

@Log4j2
@Service
public class ExamService {

	@Autowired private ExamRepository repository;
	@Autowired private SequenceRepository sequenceRepository;
	
	public Flux<Exam> findAll(final int page, final int size, int tag) {
		return this.repository.findAll(PageRequest.of(page, size, new Sort(Direction.DESC, "id")));
	}

	public Mono<Exam> get(long id) {
		return this.repository.findById(id);
	}

	public Mono<Exam> update(long id, 
			String title, String code, List<Question> questions) {
		return this.repository.findById(id).map(p -> {
			p.setTitle(title);
			p.setCode(code);
			p.setQuestions(questions);
			return p;
		}).flatMap(this.repository::save);
	}

	public Mono<Exam> delete(long id) {
		return this.repository.findById(id).flatMap(p -> this.repository.deleteById(p.getId()).thenReturn(p));
	}

	public Mono<Exam> create(String title, String code) {
		final Exam post = new Exam();
		post.setId(sequenceRepository.getNextSequenceId());
		post.setTitle(title);
		post.setCode(code);
		return this.repository.save(post);
	}
}