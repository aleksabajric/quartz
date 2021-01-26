package com.demo.quartz.service;

import com.demo.quartz.domain.tokendata.TokenData;
import org.springframework.security.core.Authentication;

public interface TokenProvider {

    String encrypt (String plainText, int expirationHours);

    String decrypt (String token);

    TokenData decryptToken(String token);

    String prepareUserDataForToken (Authentication authentication);
}
