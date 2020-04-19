package com.wx.app.game.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.Entity.WxGameDataCountEntity;
import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.FileUrlDto;
import com.wx.app.game.dto.WxGameDataDto;
import com.wx.app.game.service.UploadFileService;
import com.wx.app.game.service.WxGameDataCountService;
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

    //测试乐观锁
    @Autowired
    private WxGameDataCountService wxGameDataCountServiceImpl;

    //@NeedCheck(needCheck=true)
    @PostMapping("/wxGameDataCountServiceImpl")
    public void wxGameDataCountServiceImpl(){
        WxGameDataCountEntity byId = wxGameDataCountServiceImpl.getById(3);
        //byId.setASkipCount(10);
        //boolean create_time = wxGameDataCountServiceImpl.update(byId, new QueryWrapper<WxGameDataCountEntity>().eq("id",byId.getId()).eq("create_time", byId.getCreateTime()));

        WxGameDataDto dataDto = new WxGameDataDto();
        dataDto.setId(byId.getId());
        dataDto.setUpdatedTime(byId.getUpdatedTime().getTime());
        dataDto.setaSkipCount(100);
        int i = wxGameDataCountServiceImpl.updateData(dataDto);

        System.out.println(i);
    }

    @PostMapping("/updateDataLeLock")
    public R updateDataLeLock(@RequestBody WxGameDataDto dataDto){
        int i = wxGameDataCountServiceImpl.updateDataLeLock(dataDto);
        System.out.println(i);
        return R.ok(i);
    }

    @PostMapping("/selectByIdLock")
    public R selectById(@RequestBody WxGameDataDto dataDto){
        WxGameDataCountEntity countEntity = wxGameDataCountServiceImpl.selectByIdLock(dataDto.getId());
        System.out.println("---"+countEntity);
        return R.ok(countEntity);
    }
}
