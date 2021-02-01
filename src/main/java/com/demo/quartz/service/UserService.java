package com.demo.quartz.service;


import com.demo.quartz.dto.UserResponse;

public interface UserService {

    UserResponse findOrFetch(String email);

    UserResponse findByEmail(String email);
}
