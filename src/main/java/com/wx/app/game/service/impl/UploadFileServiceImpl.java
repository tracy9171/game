package com.wx.app.game.service.impl;

import com.wx.app.game.dto.FileUrlDto;
import com.wx.app.game.service.SystemDirService;
import com.wx.app.game.service.UploadFileService;
import com.wx.app.game.utils.GetFileNameUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 阿里对象存储类
 * @author tracyLi
 * @create 2019-10-02
 */
@Service
@Log4j2
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private SystemDirService systemDirService;

    //http://47.112.144.136:8069/     tomcat的端口号
    @Value("${wx.game.host.url:http://47.112.144.136:8080/}")
    private String hostUrl;

    /**
     * 上传文件到服务器指定位置
     * @param file
     * @param path  服务器webapps目录下文件夹路径
     * @return
     */
    @Override
    public FileUrlDto uploadFile(MultipartFile file, String path) {
        log.info("uploadFile_path={}",path);
        if (file ==null || StringUtils.isEmpty(path) || StringUtils.isEmpty(file.getOriginalFilename())){
            log.info("uploadFile入参为空");
            return null;
        }
        String bookName = file.getOriginalFilename();
        String fileName = GetFileNameUtils.getKey(bookName);
        StringBuilder paths = new StringBuilder(512);
        //linux系统
        paths.append(systemDirService.getProjectPath()).append("/").append(path).append("/").append(fileName);
        //path = testService.getProjectPath() +"\\"+"imge"+"\\"+ path; //windows系统
        log.info("上传文件fileName={}的全路径名称paths={}",fileName,paths.toString());
        File dest = new File(paths.toString());
        try {
            file.transferTo(dest);
            //http://47.112.144.136:8069/imge/123.jpg
            StringBuilder url = new StringBuilder(512);
            url.append(hostUrl).append(path).append("/").append(fileName);
            log.info("uploadFile_文件={}，上传成功！",bookName);
            return new FileUrlDto(url.toString());
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            log.error("uploadFile_error={}",e.getMessage(),e);
        }
        return null;
    }

    /**
     * 删除本地服务器中指定位置文件
     * @param fileName keyName
     * @param path 服务器webapps目录下文件夹路径
     * @return
     */
    @Override
    public boolean deleteFile(String fileName, String path) {
        StringBuilder paths = new StringBuilder(512);
        //linux系统
        paths.append(systemDirService.getProjectPath()).append("/").append(path).append("/").append(fileName);
        //windows系统
        //paths.append(systemDirService.getProjectPath()).append("\\").append(path).append("\\").append(fileName);
        log.info("删除fileName={}的全路径名称paths={}",fileName,paths.toString());
        File file = new File(paths.toString());
        return delFile(file);
    }

    //删除文件
    private boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        //删除目录下的所有文件
        /*if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }*/
        return file.delete();
    }
}
