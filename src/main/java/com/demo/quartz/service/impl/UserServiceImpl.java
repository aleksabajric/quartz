package com.demo.quartz.service.impl;

import com.demo.quartz.dao.UserDao;
import com.demo.quartz.domain.User;
import com.demo.quartz.dto.UserResponse;
import com.demo.quartz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public UserResponse findOrFetch(String email) {
        Optional<User> oplUser = userDao.findByEmail(email);
        if (oplUser.isPresent()) {
            return new UserResponse(oplUser.get());
        } else {
            User user = User.builder().email(email).build();
            userDao.save(user);
            return new UserResponse(user);
        }
    }
}
