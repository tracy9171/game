package com.wx.app.game.utils;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;


/**
 * @description: 生成token工具类
 * @author: lhq
 * @createDate: 2020/6/18
 * @version: 1.0
 */
public class TokenUtil {

    private static final String  SECRET_KEY="20ab4d0612a84078a740e0413bc01930";

    /**
     * 获取token
     * @param key
     * @return token
     */
    public static String getToken(String key){
        if (StringUtils.isEmpty(key)){
            return null;
        }
        //创建密钥
        SecretKeySpec KEY = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        //生成JWT token
        //注意：header可以不用设置，claims不能和payload同时设置。
        String payload = JSONObject.toJSONString(new TokenInfo(key));
        return Jwts.builder().setPayload(payload).signWith(SignatureAlgorithm.HS512, KEY).compact();
    }

    /**
     * 解密
     * @param token
     * @return
     */
    public static String decryptToken(String token){
        //解密JWT token内容
        SecretKeySpec KEY = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body.get("key",String.class);
    }

    @Data
    @AllArgsConstructor
    private static class TokenInfo{
        private String key;
    }
}
