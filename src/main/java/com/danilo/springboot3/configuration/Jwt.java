package com.danilo.springboot3.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class Jwt {

    @Value("${jwt.auth.app}")
    private String name;

    @Value("${jwt.auth.secret_key}")
    private String key;

    @Value("${jwt.auth.expires_in}")
    private int expiresIn;

    public String generateToken(String username) {
        byte[] secretKeyBytes = this.key.getBytes();
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(this.expiresIn);
        Date expDate = Date.from(expiration);

        String token = Jwts.builder()
            .issuer(this.name)
            .subject(username)
            .expiration(expDate)
            .issuedAt(Date.from(now))
            .signWith(secretKey)
            .compact()
        ;

        return token;
    }

    public String getUsername(String token) {
        String username;

        try {
            Claims claims = this.getClaims(token);
            username = claims.getSubject();
        }
        catch (Exception ex) {
            username = null;
        }

        return username;
    }

    public Boolean validateToken(String token,UserDetails user) {
        String username = this.getUsername(token);

        if (username != null && username.equals(user.getUsername()) && !tokenHasExpired(token))
            return true;

        return false;
    }

    public String getToken(HttpServletRequest requisicao) {
        String headerAuthentication = this.getHeaderAuthorization(requisicao);

        if (headerAuthentication != null && headerAuthentication.startsWith("Bearer "))
            return headerAuthentication.substring(7);

        return null;
    }

    private Claims getClaims(String token) {
        byte[] secretKeyBytes = this.key.getBytes();
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = parser.parseSignedClaims(token).getPayload();
        return claims;
    }

    private Boolean tokenHasExpired(String token) {
        Date expirationDate = this.getDateExpirationToken(token);
        return expirationDate.before(new Date());
    }

    private Date getDateExpirationToken(String token) {
        Date expirationDate;

        try {
            Claims claims = this.getClaims(token);
            expirationDate = claims.getExpiration();
        }
        catch (Exception ex) {
            expirationDate = null;
        }

        return expirationDate;
    }

    private String getHeaderAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
