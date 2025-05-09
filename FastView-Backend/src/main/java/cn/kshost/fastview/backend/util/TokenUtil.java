package cn.kshost.fastview.backend.util;

import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {
    public static String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // 去掉 "Bearer " 前缀
        }
        return null;
    }
}