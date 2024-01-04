package com.danilo.springboot3.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Component
public class Jwt {

    @Value("${jwt.auth.app}")
    private String name;

    @Value("${jwt.auth.secret_key}")
    private String key;

    @Value("${jwt.auth.expires_in}")
    private int expiresIn;

    private final SignatureAlgorithm SIGNATURE = SignatureAlgorithm.HS256;

    private Claims getClaims(String token) {
        Claims claims;

        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(this.key)).parseClaimsJws(token).getBody();
        }
        catch (Exception ex) {
            claims = null;
        }

        return claims;
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

    public String generateToken(String username) {
        return Jwts.builder()
            .setIssuer(this.name)
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(this.getDateExpiration())
            .signWith(this.SIGNATURE,this.key)
            .compact()
        ;
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

    private Date getDateExpiration() {
        return new Date(new Date().getTime() + this.expiresIn * 1000L);
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
