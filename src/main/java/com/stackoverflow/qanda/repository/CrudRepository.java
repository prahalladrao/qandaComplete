package com.stackoverflow.qanda.repository;

import com.stackoverflow.qanda.model.Post;
import com.stackoverflow.qanda.model.Question;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CrudRepository extends MongoRepository<Question,Long> {
//    @Query("{'Post.Answer'}")
//     public Post getAnswerByPostId(long answerId);
    List<Question> findAllBy(TextCriteria criteria);
}
