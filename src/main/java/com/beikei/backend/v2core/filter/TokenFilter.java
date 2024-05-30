package com.beikei.backend.v2core.filter;

import com.beikei.backend.v2core.config.SystemKeyword;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2module.security.cover.V2UserDetail;
import com.beikei.backend.v2module.security.orm.UserHelper;
import com.beikei.backend.v2util.CacheUtil;
import com.beikei.backend.v2util.JsonUtil;
import com.beikei.backend.v2util.JwtUtil;
import com.beikei.backend.v2util.SpringUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * accessToken解析
 *
 * @author bk
 */
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(SystemKeyword.AUTHORIZATION);
        if (StringUtils.hasText(accessToken)) {
            if (!accessToken.startsWith(SystemKeyword.BEARER)) {
                responseGameException(response,ResponseEnum.AUTHENTICATION_PARAM_ERROR);
                return;
            }
            accessToken = accessToken.replaceFirst(SystemKeyword.BEARER, "");
            Claims claims = JwtUtil.parseToken(accessToken, "");
            Long uid = Long.valueOf(String.valueOf(claims.get("sub")));
            String username = String.valueOf(claims.get("username"));
            UserHelper helper = SpringUtil.getBean(UserHelper.class);
            V2UserDetail userDetail = helper.cacheQuery(uid, username);
            if (userDetail == null) {
                responseGameException(response,ResponseEnum.AUTHENTICATION_TOKEN_ERROR);
                return;
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }


    private void responseGameException(HttpServletResponse response,ResponseEnum responseEnum) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        V2GameException exception = new V2GameException(responseEnum);
        response.getWriter().print(JsonUtil.toString(exception));
    }
}
