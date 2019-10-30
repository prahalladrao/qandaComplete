package com.stackoverflow.qanda.repository;

import com.stackoverflow.qanda.model.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostCrudRepo extends MongoRepository<Post,Long> {
    Page<Post> findAllBy(TextCriteria criteria, PageRequest pageRequest);
}
