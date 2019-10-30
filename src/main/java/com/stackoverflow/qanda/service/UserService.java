package com.stackoverflow.qanda.service;

import com.stackoverflow.qanda.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean saveUser(User user);
}
