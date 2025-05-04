package com.financialapplication.expansesanalysis.Util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

@Slf4j
@Component
public class JwtSecurity {

    private static final String SECRET_KEY_STRING = "manishpatidarmanishpatidarimaidhfaohfaishdfahsdfhsdfhsdjsdfhkjasdhflkjshfdlakjshdflkjhdsalkfjhdlskjfhaldskjfhl";
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());



    public String generateToken(String mobileNo, String email) {
        String encrypttoken = Jwts.builder().setSubject(mobileNo).claim("email", email).
                setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 300 * 5000))
                .signWith(secretKey)
                .compact();
        String token = "Bearer "+ encrypttoken;

        log.info("GENERETED TOKEN IS: {}" +token);
        return token;
    }

    public boolean validateToken(String token, String mobileNo) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return mobileNo.equals(extractMobile(token));
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }

    }

    public String extractMobile(String token)  {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }


    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}