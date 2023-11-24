package com.cms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document
@Component
public class DatabaseSequence {

	@Id
	private String dataId;

	private long seq;

	public DatabaseSequence() {

	}

	public String getId() {
		return dataId;
	}

	public void setId(String dataId) {
		this.dataId = dataId;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

}