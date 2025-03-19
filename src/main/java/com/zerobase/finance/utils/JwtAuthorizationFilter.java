package com.zerobase.finance.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.finance.dto.ResponseDto;
import com.zerobase.finance.enums.Description;
import com.zerobase.finance.enums.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        List<String> nonAuthUrls = Arrays.asList("/auth/signin", "/auth/signup");

        String requestURI = request.getRequestURI();

        if(nonAuthUrls.contains(requestURI)){
            filterChain.doFilter(request,response);
            return;
        }

        try{
            String token = parseBearerToken(request);
            if(token == null){
                throw new IllegalArgumentException();
            }
            User user = parseUserSpecification(token);
            AbstractAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(user, token, user.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException e){
            setErrorResponse(response, ErrorCode.UNAUTHORIZED);
        }catch (JwtException | IllegalArgumentException e){
            setErrorResponse(response, ErrorCode.JWT_TOKEN_INVALID);
        }
    }

    private String parseBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }
    private User parseUserSpecification(String token) {
        String[] split = Optional.ofNullable(token)
                .filter(subject -> subject.length() >= 10)
                .map(jwtUtil::validateTokenAndGetSubject)
                .orElse("anonymous:anonymous")
                .split(":");

        return new User(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        log.error(errorCode.name(), errorCode.getErrorMessage());
        ResponseDto<?> responseDto = ResponseDto.error(HttpStatus.UNAUTHORIZED, errorCode);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
