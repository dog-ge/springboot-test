package org.example.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.example.bean.*;
import org.example.dao.UserRepository;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.service.TokenBlacklistService;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody LoginRequest req) {
        User user = userRepository.findByUsername(req.getUsername()).orElse(null);
        if (user == null || !user.getEnabled()) {
            return Result.error(401, "用户名或密码错误");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return Result.error(401, "用户名或密码错误");
        }

        List<String> roleCodes = user.getRoles().stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roleCodes);

        String accessToken = JwtUtil.generateAccessToken(
                user.getId().toString(),
                user.getUsername(),
                claims
        );
        String refreshToken = JwtUtil.generateRefreshToken(user.getId().toString());

        Map<String, String> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);
        result.put("tokenType", "Bearer");
        result.put("expiresIn", "900");

        return Result.ok(result);
    }

    @PostMapping("/refresh")
    public Result<Map<String, String>> refresh(@RequestHeader("X-Refresh-Token") String refreshToken) {
        try {
            Claims claims = JwtUtil.parseRefreshToken(refreshToken);
            if (!"refresh".equals(claims.get("tokenType"))) {
                return Result.error(401, "Token类型错误");
            }

            String userId = claims.getSubject();
            User user = userRepository.findById(Long.valueOf(userId)).orElse(null);
            if (user == null || !user.getEnabled()) {
                return Result.error(401, "用户不存在或已禁用");
            }

            List<String> roleCodes = user.getRoles().stream()
                    .map(Role::getRoleCode)
                    .collect(Collectors.toList());

            Map<String, Object> accessClaims = new HashMap<>();
            accessClaims.put("roles", roleCodes);

            String newAccessToken = JwtUtil.generateAccessToken(
                    userId, user.getUsername(), accessClaims
            );

            Map<String, String> result = new HashMap<>();
            result.put("accessToken", newAccessToken);
            result.put("tokenType", "Bearer");
            result.put("expiresIn", "900");

            return Result.ok(result);

        } catch (ExpiredJwtException e) {
            return Result.error(401, "Refresh Token已过期，请重新登录");
        } catch (JwtException e) {
            return Result.error(401, "Token无效");
        }
    }

    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            long expiration = JwtUtil.getExpiration(token);
            if (expiration > 0) {
                TokenBlacklistService.blacklist(token, expiration);
            }
        }
        UserContext.remove();
        return Result.ok();
    }

    @GetMapping("/me")
    public Result<Map<String, Object>> me() {
        Map<String, Object> info = new HashMap<>();
        info.put("userId", UserContext.getUserId());
        info.put("username", UserContext.getUsername());
        info.put("roles", UserContext.getRoles());
        return Result.ok(info);
    }
}