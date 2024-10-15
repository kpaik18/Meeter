package com.example.meeter.security.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET_KEY = "fd686eb0e4d4e60d4f60948d7702ef312b6e1c9b51bbbe924e2e19e8f7caf67bcc4fea313dc56c1326a79a2db6f0b5f23cf04db3aa5c059ad89bfcd9350e7692";

    private static final int ACCESS_TOKEN_EXPIRATION_MINUTES = 60 * 60;

    private static final int REFRESH_TOKEN_EXPIRATION_MINUTES = 60 * 60;


    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, int expiration) {
        extraClaims.put("roles", userDetails.getAuthorities());
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, ACCESS_TOKEN_EXPIRATION_MINUTES);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, REFRESH_TOKEN_EXPIRATION_MINUTES);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<SimpleGrantedAuthority> getAuthorities(String token) {
        List<SimpleGrantedAuthority> result = new ArrayList<>();
        Claims claims = extractAllClaims(token);
        var objectList = (List<Object>) claims.get("roles");
        for (var obj : objectList) {
            result.add(new SimpleGrantedAuthority(((LinkedHashMap<String, String>) obj).get("authority")));
        }
        return result;
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
