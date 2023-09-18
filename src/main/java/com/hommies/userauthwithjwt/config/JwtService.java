package com.hommies.userauthwithjwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    // https://generate-random.org/
    private static final String SECRET_KEY = "c86a9b36a699c676f2bae926577051eee48ed4bae5f92c18c1e91e322312c8e6";
    // step 4
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // step 6
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // step 7
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // step 8
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // step 9
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    // step 5
    public String generateToken(Map<String, Object> extractClaims,
                                UserDetails userDetails){
        System.out.println("generating token :: " + userDetails.getUsername());
        return Jwts.
                builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))  // 24 hours
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();


    }

    // step 3
    public <T> T extractClaim(String token , Function<Claims , T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    // step 1
    public Claims extractAllClaims(String token){
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }


    // step 2
    private Key getSignInKey() {
        byte [] key = Decoders.BASE64.decode(SECRET_KEY);
      return  Keys.hmacShaKeyFor(key);
    }





}
