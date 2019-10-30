package com.stackoverflow.qanda.service;

import com.stackoverflow.qanda.model.User;
import com.stackoverflow.qanda.repository.UserCrudRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserCrudRepo userCrudRepo;
    @Override
    public boolean saveUser(User user) {
        User userInDb=userCrudRepo.save(user);
        if(userInDb==null)
            return false;
        else if(user.getUserId()!=userInDb.getUserId())
            return false;
         return true;
    }
}
