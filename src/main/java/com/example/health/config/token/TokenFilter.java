package com.example.health.config.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.health.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//filter ทุก ๆ request ของ sprint
//Tokenfilter ไม่ใช่ Bean

public class TokenFilter extends GenericFilterBean {

    private final TokenService tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //แปลงเป็น Https request
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //getHeader ที่ชื่อ  Authorization ที่ส่งมา
        String authorization = request.getHeader("Authorization");

        //เช็คว่า Authorization ส่งมาไหม ถ้าไม่ส่งก็ปล่อยผ่าน
        if(ObjectUtils.isEmpty(authorization)){
            //มี request อะไรมาก็ส่งกลับไปเหมือนเดิม
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //ประเภท Bearer ไหม
        if(!authorization.startsWith("Bearer ")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //ดีง Token
        String token = authorization.substring(7);

        //เช็คว่า มาจาก token เราจริง ๆ และยังไม่หมดอายุ พร้อมถอดรหัส
        DecodedJWT decoded = tokenService.verifyToken(token);
        if(decoded == null){
            filterChain.doFilter(servletRequest,servletResponse);
            //บังคับออก function
            return;
        }
        //user id ดึงค่า
        String principal = decoded.getClaim("principal").asString();
        String role = decoded.getClaim("role").asString();
        //role of spring
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        //ระบุว่า user login แล้วนะ ก้วยค่า authenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal,"protected",authorities);

        //ระบุว่า user login แล้วนะ ก้วยค่า authenticationToken
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
