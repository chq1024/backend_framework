package com.beikei.backend.v2core.config;

import com.beikei.backend.v2core.exception.V2AccessDeniedHandler;
import com.beikei.backend.v2core.exception.V2AuthenticationHandler;
import com.beikei.backend.v2core.filter.TenantFilter;
import com.beikei.backend.v2core.filter.TokenFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * SpringSecurityConfig配置
 * @author bk
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().cacheControl().disable()
                .and()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new TenantFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenFilter(),UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(
                        auth -> auth.antMatchers("/v2/u/login", "/v2/u/logout","/v2/u/pubKey", "/v2/openapi/**")
                                .permitAll().anyRequest().authenticated())
                .exceptionHandling()
                .authenticationEntryPoint(new V2AuthenticationHandler())
                .accessDeniedHandler(new V2AccessDeniedHandler());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
