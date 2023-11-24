package com.cms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cms.model.Associate;

public interface AssociateRepository extends MongoRepository<Associate, String> {
           List<Associate> findAll();
}
