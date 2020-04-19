package com.wx.app.game.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author tracyLi
 * @create 2019-09-08 14:33
 */
public class PageUtils2 {
    public static JSONObject getResultPageList(IPage page){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","查询成功");
        jsonObject.put("code","0");
        jsonObject.put("count",page.getTotal());
        jsonObject.put("data",page.getRecords());
        return jsonObject;
    }
    public static JSONObject isNull(JSONObject jsonObject){
        jsonObject.put("code","-1");
        jsonObject.put("msg","无数据");
        return jsonObject;
    }
    public static JSONObject isError(JSONObject jsonObject){
        jsonObject.put("code","-1");
        jsonObject.put("msg","查询异常");
        return jsonObject;
    }
}
