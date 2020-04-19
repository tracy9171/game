package com.wx.app.game.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @ClassName CheckPramsDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-20 21:43
 * @Version 1.0
 */
@Slf4j
public class CheckPramsUtils {
    /**
     * 多参数非空校验
     * @param params
     * @return 返回true 参数有空值，false时均不为空
     */
    public static boolean isEmpty(String ...params){
        if (params == null)return true;
        for (String a :params){
            if (StringUtils.isEmpty(a))return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isEmpty(null));
        System.out.println(isEmpty(""));
        System.out.println(isEmpty("a",""));
        System.out.println(isEmpty("a",null));
        System.out.println(isEmpty("a","b"));
    }
}
