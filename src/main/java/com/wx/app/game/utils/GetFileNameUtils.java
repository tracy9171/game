package com.wx.app.game.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 生成文件
 */
public class GetFileNameUtils {
    public static String getKey(String originalFilename){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-","")+originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
