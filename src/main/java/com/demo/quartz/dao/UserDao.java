package com.demo.quartz.dao;

import com.demo.quartz.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
