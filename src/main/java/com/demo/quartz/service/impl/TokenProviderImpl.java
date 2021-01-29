package com.demo.quartz.service.impl;

import com.demo.quartz.domain.tokendata.TokenData;
import com.demo.quartz.exception.AuthenticationException;
import com.demo.quartz.exception.PermissionException;
import com.demo.quartz.service.helpers.DateHandlerService;
import com.demo.quartz.service.ObjectMapperWrapper;
import com.demo.quartz.service.TokenProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenProviderImpl implements TokenProvider {


    private final ObjectMapperWrapper objectMapperWrapper;
    private final Logger logger = LoggerFactory.getLogger(TokenProviderImpl.class);

    @Override
    public String encrypt(String plainText, int expirationHours) {
        String token = Jwts.builder().setSubject(plainText)
                .signWith(SignatureAlgorithm.HS512,
                        TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
                .setExpiration(DateHandlerService.addHours(expirationHours))
                .compact();
        logger.info("New token: {}", token);
        return token;
    }

    @Override
    public String decrypt(String token) {
        try {
            String text = Jwts.parser().setSigningKey(TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            logger.info("Text from token: {}", text);
            return text;
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("Token expired!");
        } catch (IllegalArgumentException | SignatureException | UnsupportedJwtException | MalformedJwtException e) {
            throw new PermissionException("Invalid token!");
        }
    }

    @Override
    public TokenData decryptToken(String token) {
        return objectMapperWrapper.decodeFromJson(decrypt(token), TokenData.class);
    }

    @Override
    public String prepareUserDataForToken (Authentication authentication) {
        Map<String , Object> userDetails = ((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttributes();
        return objectMapperWrapper
                .encodeToJson(new TokenData((String) userDetails.get("name"), (String) userDetails.get("email")));
    }
}
