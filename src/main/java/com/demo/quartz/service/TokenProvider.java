package com.demo.quartz.service;

import org.springframework.security.core.Authentication;

public interface TokenProvider {

    String encrypt (String plainText, int expirationHours);

    String decrypt (String token);

    String prepareUserDataForToken (Authentication authentication);
}
