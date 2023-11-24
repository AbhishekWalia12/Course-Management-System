package com.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cms.model.DatabaseSequence;

@Service
public class SequenceGeneratorService {

	@Autowired
	private MongoOperations mongoOperations;

	public String generateSequence(String sequenceName) {
		Query query = new Query(Criteria.where("_id").is(sequenceName));
		Update update = new Update().inc("seq", 1);
		FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
		DatabaseSequence counter = mongoOperations.findAndModify(query, update, options, DatabaseSequence.class);
		if (counter == null) {
			counter = new DatabaseSequence();
			counter.setId("associate_sequence");
			counter.setSeq(100L); // Set initial value here
			mongoOperations.save(counter);
		}
		long sequence = counter.getSeq();
	    return String.format("%03d", sequence);
	}
    
}