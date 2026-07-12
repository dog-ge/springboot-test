package org.example.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.example.bean.UserContext;
import org.example.service.TokenBlacklistService;
import org.example.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            writeError(response, 401, "缺少Token");
            return false;
        }

        String token = header.substring(7);

        if (!TokenBlacklistService.isBlacklisted(token)) {
            try {
                Claims claims = JwtUtil.parseAccessToken(token);

                if (!"access".equals(claims.get("tokenType"))) {
                    writeError(response, 401, "Token类型错误");
                    return false;
                }

                String userId = claims.getSubject();
                String username = (String) claims.get("username");

                List<String> roles = (List<String>) claims.get("roles");
                if (roles == null) {
                    roles = new ArrayList<>();
                }

                UserContext.set(new UserContext.CurrentUser(userId, username, roles));
                return true;

            } catch (ExpiredJwtException e) {
                writeError(response, 401, "Token已过期");
                return false;
            } catch (JwtException e) {
                writeError(response, 401, "Token无效");
                return false;
            }
        } else {
            writeError(response, 401, "Token已失效");
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.remove();
    }

    private void writeError(HttpServletResponse response, int code, String msg) throws Exception {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":" + code + ",\"msg\":\"" + msg + "\"}");
    }
}