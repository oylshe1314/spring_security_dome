package com.example.dome.application.util;

import com.example.dome.application.auth.UserAuthDetails;
import com.example.dome.application.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private Key secretKey;

    private int expireTime;

    @Value("${user.token.secret-key}")
    public void setSecretKey(Key secretKey) {
        this.secretKey = secretKey;
    }

    @Value("${user.token.expire-time}")
    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String generateToken(UserAuthDetails authDetails) {

        User user = authDetails.getUser();

        JwtBuilder jwtBuilder = Jwts.builder();

        Map<String, Object> body = new HashMap<>();

        body.put("userId", user.id);
        body.put("username", user.username);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireTime * 1000);

        jwtBuilder.setSubject("token");
        jwtBuilder.setAudience("user");
        jwtBuilder.setIssuedAt(now);
        jwtBuilder.setExpiration(expiryDate);
        jwtBuilder.addClaims(body);

        return jwtBuilder.signWith(secretKey).compact();
    }

    private Map<String, Object> validateToken(String token) throws Exception {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new Exception("无效的TOKEN");
        }
    }

    public Long getUserId(String token) throws Exception {
        Number userId = ((Number) this.validateToken(token).get("userId"));
        if (userId == null || userId.longValue() == 0) {
            throw new Exception("无效的TOKEN");
        }
        return userId.longValue();
    }

    public String getUsername(String token) throws Exception {
        String username = (String) this.validateToken(token).get("username");
        if (username == null || username.isEmpty()) {
            throw new Exception("无效的TOKEN");
        }
        return username;
    }
}
