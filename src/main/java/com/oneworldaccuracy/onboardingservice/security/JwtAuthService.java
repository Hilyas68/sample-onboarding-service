package com.oneworldaccuracy.onboardingservice.security;

import com.oneworldaccuracy.onboardingservice.util.Constants;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtAuthService {

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: {}" + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: {}" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: {}" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: {}" + e.getMessage());
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
