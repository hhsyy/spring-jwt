package com.yiyuclub.springjwt.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
    java-jwt 包
 */
public class JwtUtils {
    //过期时间
    private static final long DATE_EXPIRE = 30 * 60 * 1000;
    //私钥
    private static final String TOKEN_SECRET = "yiyu_club";

    /*
    iss：发行人
    exp：到期时间
    sub：主题
    aud：用户
    nbf：在此之前不可用
    iat：发布时间
    jti：JWT ID用于标识该JWT
     */
    public static String getToken(String name, String userinfo) {
        try {
            Date date = new Date(System.currentTimeMillis() + DATE_EXPIRE);

            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            //头部
            HashMap<String, Object> header = new HashMap<>();
            header.put("Type", "Jwt");
            header.put("alg", "HS256");

            String sign = JWT.create()
                    .withHeader(header)
                    .withIssuer("yiyu")
                    .withClaim("name", name)
                    .withClaim("userinfo", userinfo)
                    .withExpiresAt(date)
                    .sign(algorithm);

            //这里可将id和token存入redis（reflesh_token），过期时间2t
            //下一次请求如果web的token过期，可于redis中找到reflesh_token
            //有则重新得到新的token返回web，无则返回重新登陆

            return sign;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTokenWithDate(String name, String userinfo, long d) {
        try {
            Date date = new Date(System.currentTimeMillis() + d);

            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            //头部
            Map<String, Object> header = new HashMap<>();
            header.put("Type", "Jwt");
            header.put("alg", "HS256");

            String sign = JWT.create()
                    .withHeader(header)
                    .withIssuer("yiyu")
                    .withClaim("name", name)
                    .withClaim("userinfo", userinfo)
                    .withExpiresAt(date)
                    .sign(algorithm);

            return sign;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean checkToken(String token) {
        DecodedJWT jwt = null;
        try {
            Map<String, Object> header = new HashMap<>();
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            JWTVerifier verifier =
                    JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                            .withIssuer("yiyu")
                            .build();
            jwt = verifier.verify(token);
            if (jwt.getClaims() != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static Map<String, Claim> getTokenDetail(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier =
                    JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                            .withIssuer("yiyu")
                            .build();
            jwt = verifier.verify(token);

            return jwt.getClaims();
        } catch (Exception e) {
            return null;
        }
    }
}
