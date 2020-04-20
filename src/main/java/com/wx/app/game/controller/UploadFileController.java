package com.wx.app.game.controller;

import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.FileUrlDto;
import com.wx.app.game.service.UploadFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @ClassName UploadFileController
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-28 13:15
 * @Version 1.0
 */
@Api(description = "图片上传")
@CrossOrigin
@RestController
@RequestMapping("/wx/upload")
public class UploadFileController {

    @Autowired
    private UploadFileService UploadFileServiceImpl;

    @NeedCheck(needCheck=true)
    @ResponseBody
    @ApiOperation(value = "图片上传")
    @PostMapping("/getPicUrl")
    public FileUrlDto uploadPic(@RequestParam("file") MultipartFile file){
        return UploadFileServiceImpl.uploadFile(file,"imges");
    }

}
