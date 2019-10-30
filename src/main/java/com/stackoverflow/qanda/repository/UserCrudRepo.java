package com.stackoverflow.qanda.repository;

import com.stackoverflow.qanda.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCrudRepo extends MongoRepository<User,Long> {
       User  findByUserName(String userName);
}
