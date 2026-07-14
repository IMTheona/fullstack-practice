package com.theona;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    // 生成JWT代码
    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");

        String Token = JWT.create()
                .withClaim("user",claims) // 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))
                .sign(Algorithm.HMAC256("theona"));

        System.out.println(Token);
    }

    @Test
    public void testParse(){
        String Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3ODQwNTc4Nzl9" +
                ".yfaPuk86vim2r7hzd00xZAldWtkNvt0Q24KOUGcFPB4";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("theona")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(Token);// 验证Token，生成一个解析后的JWT对象
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));;
    }
}
