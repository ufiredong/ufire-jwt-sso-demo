package com.ufire.authsso.jwt;
import com.alibaba.fastjson.JSONObject;
import com.ufire.authsso.model.RestModel;
import com.ufire.authsso.model.UserInfo;
import com.ufire.authsso.tools.RSAUtil;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import java.security.PrivateKey;
import java.security.PublicKey;
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
    public static String generateToken(String userInfo, PrivateKey privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim("user", userInfo)
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
    public static RestModel parserToken(String token, PublicKey publicKey) {
        RestModel restModel = new RestModel();
        try {
            Jws<Claims> res = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            long expire = res.getBody().getExpiration().getTime();
            long now = DateTime.now().toDate().getTime();
            long a = expire - now;
            if (a < 0) {
                restModel.setErrorCode(1);
                restModel.setErrorMessage("TOKEN已经过期,重定向回sso");
                return restModel;
            }
            // 30分钟有效期 距离过期10分钟内即将过期 刷新token
            long b= a / 1000 / 60 ;
            if (b < 10) {
                restModel.setErrorCode(2);
                restModel.setData(res.getBody().get("user"));
                restModel.setErrorMessage("TOKEN还剩"+b+"分钟,即将过期，刷新token");
                return restModel;
            }
            restModel.setData(res.getBody().get("user"));
        } catch (Exception e) {
            restModel.setErrorCode(1);
            restModel.setErrorMessage("解析TOKEN发生异常,重定向回sso");
            return restModel;
        }
        return restModel;
    }

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }


    public static void main(String[] args) throws Exception {
        // 预定义 几个 永久refresh-token
        PrivateKey privateKey = RSAUtil.getPrivateKey("D:\\ufire\\ufire-jwt-sso-demo\\auth-server\\src\\main\\resources\\key\\rsa-jwt.pri");
        String token = generateToken("client", privateKey, 99999);

        System.out.println(token);

    }


}
