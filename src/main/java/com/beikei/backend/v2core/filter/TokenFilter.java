package com.beikei.backend.v2core.filter;

import com.beikei.backend.v2core.config.SystemKeyword;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2util.CacheUtil;
import com.beikei.backend.v2util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * accessToken解析
 * @author bk
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(SystemKeyword.AUTHORIZATION);
        if (StringUtils.hasText(accessToken)) {
            if (!accessToken.startsWith(SystemKeyword.BEARER)) {
                throw new V2GameException(ResponseEnum.AUTHENTICATION_PARAM_ERROR);
            }
            accessToken = accessToken.replaceFirst(SystemKeyword.BEARER, "");
            Claims claims = JwtUtil.parseToken(accessToken);
            boolean pass = JwtUtil.validToken(claims);
            if (pass) {
                // 加载当前内存中对应用户的的信息作为当前线程的认证信息

//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                        user, null, user.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
