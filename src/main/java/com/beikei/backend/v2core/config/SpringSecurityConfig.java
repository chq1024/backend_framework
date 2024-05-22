package com.beikei.backend.v2core.config;

import com.h7culture.manage.v2core.exception.V2AccessDeniedHandler;
import com.h7culture.manage.v2core.exception.V2AuthenticationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SpringSecurityConfig配置
 * @author bk
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().cacheControl().disable()
                .and()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(
                        auth -> auth.antMatchers("/v2/u/login", "/v2/u/u/logout","/v2/u/publicKey", "/v2/openapi/**")
                                .permitAll().anyRequest().authenticated())
                .exceptionHandling().authenticationEntryPoint(new V2AuthenticationHandler())
                .accessDeniedHandler(new V2AccessDeniedHandler());
        return http.build();
    }
}
