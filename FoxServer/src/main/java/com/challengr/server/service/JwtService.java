package com.challengr.server.service;

import com.challengr.server.dto.UserDto;
import com.challengr.server.model.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    /**
     * Извлечение имени пользователя из токена
     *
     * @param token токен
     * @return имя пользователя
     */
    public String extractUserID(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Генерация токена
     *
     * @param user данные пользователя
     * @return токен
     */
    public String generateToken(UserDto user, Duration validity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("auth", user.getAuthorities());
        return generateToken(claims, user, validity);
    }

    /**
     * Проверка токена на валидность
     *
     * @param token       токен
     * @param userId        данные пользователя
     * @return true, если токен валиден
     */
    public boolean isTokenValid(String token, Long userId) {
        final String userID = extractUserID(token);
        System.out.println("d" + userID + " " + isTokenExpired(token));
        return (userID.equals(userId + "")) && !isTokenExpired(token);
    }

    /**
     * Извлечение данных из токена
     *
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Генерация токена
     *
     * @param extraClaims дополнительные данные
     * @param user        данные пользователя
     * @return токен
     */
    private String generateToken(Map<String, Object> extraClaims, UserDto user, Duration validity) {
        return Jwts.builder().claims()
                .add(extraClaims)
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(Instant.now().plus(validity)))
                .and().signWith(getSigningKey()).compact();
    }

    /**
     * Проверка токена на просроченность
     *
     * @param token токен
     * @return true, если токен просрочен
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token токен
     * @return дата истечения
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Claims extractAllClaims(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey));
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Получение ключа для подписи токена
     *
     * @return ключ
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}