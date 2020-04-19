package com.wx.app.game.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author tracyLi
 * @create 2019-09-08 14:33
 */
public class InitPageUtils {
    public static Page getPage(Integer size,Integer current){
        Page page = new Page<>();
        if (size == null || current == null){
            page.setCurrent(1);
            page.setSize(10);
        }else {
            page.setCurrent(current);
            page.setSize(size);
        }
        return page;
    }
}
