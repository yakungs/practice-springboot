package com.example.health.config.token;

import com.example.health.service.TokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenFilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenService service;

    public TokenFilterConfig(TokenService service) {
        this.service = service;
    }

    //จะฟิลเตอร์ UsernamePasswordAuthenticationFilter ด้วยฟิวเตอร์ที่ส่งให้มา
    @Override
    public void configure(HttpSecurity http) throws Exception {

        TokenFilter filter = new TokenFilter(service);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    }
}
