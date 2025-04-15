package cn.kshost.fastview.backend.security;

import cn.hutool.json.JSONUtil;
import cn.kshost.fastview.backend.util.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证失败 AuthenticationEntryPoint方法
 */
@Component
public class LoginAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String notLogin = JSONUtil.toJsonStr(Result.error(-1, "用户未登录"));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(notLogin);
    }
}