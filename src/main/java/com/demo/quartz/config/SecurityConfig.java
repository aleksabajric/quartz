package  com.demo.quartz.config;

import com.demo.quartz.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BearerTokenFilter tokenFilter;
    private final TokenProvider tokenProvider;
    @Value("${token.duration}")
    private Integer tokenDuration;

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers( "/oauth2/**", "/login**" ).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .and()
                .successHandler( this::successHandler )
                .and().logout(log -> log.addLogoutHandler( this::logout ).logoutSuccessHandler( this::onLogoutSuccess ));
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) {
        // token here
        System.out.println("Auth token is - " + request.getHeader( "Authorization" ));
    }

    void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                         Authentication authentication) throws IOException, ServletException {
        response.setStatus( HttpServletResponse.SC_OK );
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods( Collections.singletonList( "*" ) );
        config.setAllowedOrigins( Collections.singletonList( "*" ) );
        config.setAllowedHeaders( Collections.singletonList( "*" ) );

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", config );
        return source;
    }


    private void successHandler( HttpServletRequest request,
                                 HttpServletResponse response, Authentication authentication ) throws IOException {
        String token = tokenProvider.encrypt(tokenProvider.prepareUserDataForToken(authentication), tokenDuration);
        response.sendRedirect("http://localhost:4200/home?token=" + token);
    }

}