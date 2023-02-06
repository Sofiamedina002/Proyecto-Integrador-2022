package com.dh.userprofile.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String ALL_ENDPOINTS_PATTERN = "/**";

    @Value(value = "${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;
    private List<String> skipPaths;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;

//    public void setSkipPaths(List<String> skipPaths) {
//        this.skipPaths = skipPaths;
//    }
//
//    public void setAllowedOrigins(List<String> allowedOrigins) {
//        this.allowedOrigins = allowedOrigins;
//    }
//
//
//    public void setAllowedMethods(List<String> allowedMethods) {
//        this.allowedMethods = allowedMethods;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors(withDefaults())
                .csrf().disable()
                .logout().disable()
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .decoder(jwtDecoder());
    }

    @Bean
    protected JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }
}
