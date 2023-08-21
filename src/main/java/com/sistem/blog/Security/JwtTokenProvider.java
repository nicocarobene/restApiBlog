package com.sistem.blog.Security;

import com.sistem.blog.exceptions.BlogAppException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private String jwtExpirations;

    private Key getKey(){
        byte[] keybyte= Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keybyte);
    }
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        System.out.println("Desde generate token: "+username);
        return Jwts
                    .builder()
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ Long.parseLong(jwtExpirations)))
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "token is not valid");
        } catch (ExpiredJwtException e) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "token is expiration");
        } catch (UnsupportedJwtException e) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "token JWT not valid");
        }catch (IllegalArgumentException e){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "claims JWT is empty");
        }
    }
}
