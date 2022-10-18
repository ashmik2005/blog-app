package com.jbdl.blogapp.security;


import com.jbdl.blogapp.exception.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    // Method to generate a JWT token
    public String generateToken(Authentication authentication){

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();


        return token;
    }

    // Method to get username from a jwt token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Validate JWT Token
    public boolean validateToken(String token) {
        try{
             Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
             return true;
        } catch (SignatureException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT Signature is invalid");
        } catch(MalformedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid token");
        } catch(ExpiredJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT Token has expired");
        } catch(UnsupportedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch(IllegalArgumentException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims token is invalid");
        }
    }
}
