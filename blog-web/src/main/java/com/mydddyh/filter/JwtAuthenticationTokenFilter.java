package com.mydddyh.filter;

import com.alibaba.fastjson.JSON;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.entity.LoginUser;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.utils.JwtUtil;
import com.mydddyh.utils.RedisCache;
import com.mydddyh.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader(SystemConstants.HEADER_TOKEN_FIELD);
        if(!StringUtils.hasText(token)){
            //该接口不需要登录, 直接放行
            filterChain.doFilter(request, response);
            return;
        }

        //解析获取userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token非法或token超时, 提示重新登录
            needLogin(response);
            return;
        }
        String userId = claims.getSubject();

        //从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject(SystemConstants.WEB_USERID_REDIS_PREFIX + userId);
        if(Objects.isNull(loginUser)){
            // Redis过期, 提示重新登录
            needLogin(response);
            return;
        }

        //存入SecurityContextHolder供后续filter使用
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 放行
        filterChain.doFilter(request, response);
        return;
    }

    private void needLogin(HttpServletResponse response) {
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        WebUtils.renderString(response, JSON.toJSONString(result));
        return;
    }
}
