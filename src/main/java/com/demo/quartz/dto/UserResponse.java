package com.demo.quartz.dto;

import com.demo.quartz.domain.User;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String lastName;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.lastName = user.getLastName();
        this.name = user.getFirstName();
    }
}
