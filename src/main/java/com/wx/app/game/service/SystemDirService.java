package com.wx.app.game.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SystemDirService {
    public String getProjectPath(){
        /**
         * System.getProperty("user.dir")
         * 当前tomcat的bin目录的路径 如 D:\java\software\apache-tomcat-6.0.14\bin
         */
        String dir=System.getProperty("user.home"); //把bin 文件夹变到 webapps文件里面
        log.info("获取系统webAppsDir={}",dir);
        return dir;
    }
}
