package com.ufire.authsso.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ufire.authsso.constant.Constant;
import com.ufire.authsso.model.UserInfo;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @title: JwtUtil
 * @Author ufiredong
 * @Date: 2021/9/28 10:38
 * @Des:
 * @Version 1.0
 */
public class JwtUtil {


    /**
     * 私钥创建 token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(UserInfo userInfo, PrivateKey privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .setSubject(userInfo.getUserName())
                .claim("user", JSONObject.toJSONString(userInfo))
                .setId(createJTI())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    /**
     * 公钥解析 token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

}
