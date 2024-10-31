package com.jwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {

    //we can keep the secret key in application properties as well
    private static final String Secret_Key = "B1F2A3E4567C89D01234F5A6789B0CDE01234567A89BCDEF1234ABCDE7890F12";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //generic method
    //
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Secret_Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
