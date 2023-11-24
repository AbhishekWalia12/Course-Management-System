package com.cms.service;

import org.springframework.data.mongodb.core.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import com.cms.model.DatabaseSequence;


@Service
public class SequenceGeneratorService {
	     
	    @Autowired
	    MongoOperations operations;
	    
	    public long getNextSequence(String sequenceName) {
	    	
	    	Query query = new Query(Criteria.where("_id").is(sequenceName));
	        Update update = new Update().inc("seq", 1);
	        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
	        DatabaseSequence counter = operations.findAndModify(query, update, options, DatabaseSequence.class);
	        if (counter == null) {
	            counter = new DatabaseSequence();
	            counter.setId(sequenceName);
	            counter.setSeq(300L); // Set initial value here
	            operations.save(counter);
	        }
	        return counter.getSeq();
	    }
	   
	
	
    
}