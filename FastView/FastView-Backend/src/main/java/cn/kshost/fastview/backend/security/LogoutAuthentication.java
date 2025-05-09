package cn.kshost.fastview.backend.security;

import cn.hutool.json.JSONUtil;
import cn.kshost.fastview.backend.pojo.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
@Component
public class LogoutAuthentication  implements LogoutSuccessHandler {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = request.getHeader("token");
        if (Objects.nonNull(token)) {
            if (redisTemplate.hasKey("user:login:accessToken:"+token)) {
                redisTemplate.delete("user:login:accessToken:"+token);
            }
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String jsonStr = JSONUtil.toJsonStr(Result.success("注销成功", null));
        response.getWriter().write(jsonStr);
    }
}