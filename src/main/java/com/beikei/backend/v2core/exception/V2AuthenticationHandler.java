package com.beikei.backend.v2core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 发送一个要求客户端提供凭证的HTTP响应
 * @author bk
 */
@Slf4j
public class V2AuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String remoteUser = request.getRemoteUser();
        String requestURI = request.getRequestURI();
        log.error("{} not has auth {}",remoteUser,requestURI);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        response.getWriter().print("");
    }
}
