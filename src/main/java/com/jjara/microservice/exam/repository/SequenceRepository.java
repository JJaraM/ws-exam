package com.jjara.microservice.exam.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.jjara.microservice.exam.pojo.Sequence;

@Repository
public class SequenceRepository {

	@Autowired private MongoOperations mongoOperation;
	
	private final String KEY = "exam";

	public long getNextSequenceId() {

		Query query = new Query(Criteria.where("_id").is(KEY));

		Update update = new Update();
		update.inc("seq", 1);

		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		Sequence seqId = mongoOperation.findAndModify(query, update, options, Sequence.class);

		return seqId.getSeq();

	}

}
