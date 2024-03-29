package com.example.springjwt.common.login.jwt;

import com.example.springjwt.entity.UserInfoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setUsernameParameter("id");
        setPasswordParameter("password");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // 요청 본문에서 JSON 데이터를 읽어와 User 객체로 변환
            UserInfoEntity loginRequest = new ObjectMapper().readValue(request.getInputStream(), UserInfoEntity.class);

            // User 객체에서 username과 password 추출
            String username = loginRequest.getNAME();
            String password = loginRequest.getPASSWORD();

            System.out.println(username);
            System.out.println(loginRequest);
            System.out.println(password);

            // 추출한 정보를 바탕으로 UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

            // AuthenticationManager를 사용하여 인증 시도
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to parse authentication request body", e);
        }
//        String username = request.
//        String password = obtainPassword(request);
//
//        System.out.println(obtainUsername(request));
////        log.info(String.valueOf(obtainUsername(request)));
//
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
//
//        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        //유저 정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
       //String role = auth.getAuthority();

        //토큰 생성
        String access = jwtUtil.createJwt("access", username, 600000L);
        String refresh = jwtUtil.createJwt("refresh", username, 86400000L);

        //응답 설정
//        response.setHeader("access", access);
        response.addCookie(createCookie("refreshToken", refresh));
        response.addCookie(createCookie("accessToken", access));
        response.setStatus(HttpStatus.OK.value());

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true); //https 통신일경우 설정
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}
