package com.malgn.configure.jwt;

import com.malgn.api.auth.service.UserPrincipal;
import com.malgn.exception.CustomException;
import com.malgn.exception.ResponseCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {
    private final SecretKey key;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access_exp}") long accessTokenExpTime,
            @Value("${jwt.refresh_exp}") long refreshTokenExpTime
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    public String createAccessToken(UserPrincipal userPrincipal) {
        return createToken(userPrincipal, accessTokenExpTime);
    }

    public String createRefreshToken(UserPrincipal userPrincipal) {
        return createToken(userPrincipal, refreshTokenExpTime);
    }


    private String createToken(UserPrincipal userPrincipal, long expireTime) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .claim("username", userPrincipal.getUsername())
                .claim("id", userPrincipal.getId())
                .claim("role", userPrincipal.getRole())
                .issuedAt(Date.from(now.toInstant()))
                .expiration(Date.from(tokenValidity.toInstant()))
                .signWith(key)
                .compact();
    }


    public Long getUserId(String token) {
        Number id = parseClaims(token).get("id", Number.class);
        return id.longValue();
    }
    public String getUsername(String token) {
        return parseClaims(token).get("username", String.class);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException e) {
            throw new CustomException(ResponseCode.INVALID_TOKEN); // Invalid JWT signature
        } catch (MalformedJwtException e) {
            throw new CustomException(ResponseCode.INVALID_TOKEN); // Invalid JWT signature
        } catch (ExpiredJwtException e) {
            throw new CustomException(ResponseCode.EXPIRED_TOKEN); // Expired JWT token
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ResponseCode.UNSUPPORTED_TOKEN); // Unsupported JWT token
        } catch (IllegalArgumentException e) {
            throw new CustomException(ResponseCode.INVALID_HEADER_OR_COMPACT_JWT); // JWT token compact of handler are invalid
        }
    }
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}