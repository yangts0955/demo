package com.epam.demo.configuration.interceptor.security.filter;

import com.alibaba.fastjson.JSON;
import com.epam.demo.Entity.LoginUser;
import com.epam.demo.Entity.enumeration.ExceptionMessageEnum;
import com.epam.demo.configuration.interceptor.exception.httpException.UnauthorizedException;
import com.epam.demo.utils.JwtUtil;
import com.epam.demo.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    private static final String REDIS_CACHE_KEY_HEAD = "login:";

    private static final String TOKEN_STR = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader(TOKEN_STR);
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userid;
        LoginUser loginUser;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            loginUser = JSON.parseObject(claims.getSubject(), LoginUser.class);
            userid = String.valueOf(loginUser.getEmployee().getId());
        } catch (Exception e) {
            resolver.resolveException(request, response, null,
                    new UnauthorizedException(ExceptionMessageEnum.INCORRECT_TOKEN));
            return;
        }
        //从redis中获取用户信息
        String redisKey = REDIS_CACHE_KEY_HEAD + userid;
        String token_redis = redisCache.getCacheObject(redisKey);
        if(token_redis == null || token_redis.isEmpty()){
            resolver.resolveException(request, response, null,
                    new UnauthorizedException(ExceptionMessageEnum.UNAUTHORIZED));
            return;
        }
        if (!token_redis.equals(token)){
            resolver.resolveException(request, response, null,
                    new UnauthorizedException(ExceptionMessageEnum.UNAUTHORIZED));
            return;
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        //放行
        filterChain.doFilter(request, response);
    }
}
