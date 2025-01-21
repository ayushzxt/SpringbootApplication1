package com.example.demo.repo;

import com.example.demo.model.StatusCodeMain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusCodeMainRepository extends MongoRepository<StatusCodeMain, String> {
}
