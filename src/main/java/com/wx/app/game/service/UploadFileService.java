package com.wx.app.game.service;

import com.wx.app.game.dto.FileUrlDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传类
 */
public interface UploadFileService {
    FileUrlDto uploadFile(MultipartFile file, String path);
    boolean deleteFile(String fileName, String path);
}
