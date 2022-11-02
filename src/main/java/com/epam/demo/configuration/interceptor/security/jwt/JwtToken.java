//package com.epam.demo.configuration.interceptor.security.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
//@Component
//public class JwtToken {
//
//    private static String jwtKey;
//    private static Integer expiredTimeIn;
//    private static Integer defaultScope = 8;
//
//    public static Optional<Map<String, Claim>> getClaims(String token) {
//        DecodedJWT decodedJWT;
//        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
//        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
//
//        try {
//            decodedJWT = jwtVerifier.verify(token);
//        } catch (JWTVerificationException e) {
//            return Optional.empty();
//        }
//        return Optional.of(decodedJWT.getClaims());
//    }
//
//    public static Boolean verifyToken(String token) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
//            JWTVerifier verifier = JWT.require(algorithm).build();
//            verifier.verify(token);
//        } catch (JWTVerificationException e) {
//            return false;
//        }
//        return true;
//    }
//
//    public static String getToken(int uid, Integer scope) {
//        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
//        Map<String, Date> map = JwtToken.calculateExpiredTimeIn();
//
//        return JWT.create()
//                .withClaim("uid", uid)
//                .withClaim("scope", scope)
//                .withIssuedAt(map.get("now"))
//                .withExpiresAt(map.get("expiredTime"))
//                .sign(algorithm);
//    }
//
//    public static String makeToken(int uid, Integer scope) {
//        return JwtToken.getToken(uid, scope);
//    }
//
//    public static String makeToken(int uid) {
//        return JwtToken.getToken(uid, defaultScope);
//    }
//
//    private static Map<String, Date> calculateExpiredTimeIn() {
//        Map<String, Date> map = new HashMap<>();
//        Calendar calendar = Calendar.getInstance();
//        Date now = calendar.getTime();
//
//        //calculate the expired time
//        calendar.add(Calendar.SECOND, JwtToken.expiredTimeIn);
//        map.put("now", now);
//        map.put("expiredTime", calendar.getTime());
//        return map;
//    }
//
//    @Value("${jwt.jwt-key}")
//    public void setJwtKey(String jwtKey) {
//        JwtToken.jwtKey = jwtKey;
//    }
//
//    @Value("${jwt.token-expired-in}")
//    public void setExpiredTimeIn(Integer expiredTimeIn) {
//        JwtToken.expiredTimeIn = expiredTimeIn;
//    }
//}
