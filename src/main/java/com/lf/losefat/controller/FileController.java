package com.lf.losefat.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.losefat.entity.AvatarFiles;
import com.lf.losefat.entity.User;
import com.lf.losefat.service.IFileService;
import com.lf.losefat.service.IUserService;
import com.lf.losefat.utils.TokenUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * <p>
 * 文件管理 前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2024-03-24
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}") //yml配置文件中的文件保存路径
    private String fileUploadPath;

//     @Value("${files.upload.upload}") //yml配置文件中的文件保存路径
//    private String ip;


    @Autowired
    private IFileService fileService;

    @Autowired
    private IUserService userService;
    @PostMapping("/upload")
    public String upFile(@RequestParam MultipartFile file) throws IOException {
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件类型（后缀名）
        String type = FileUtil.extName(originalFilename);
        //文件大小
        long size = file.getSize();
        //获取当前用户信息
        User currentUser = TokenUtils.getCurrentUser();
        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;  //StrUtil.DOT ==>uuid + . + png
        File uploadFile = new File(fileUploadPath + fileUUID);

        //判断父级目录（/files/ + 1.png）是否存在，否则新建改文件目录
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        //获取文件下载路径
        String url;
        //把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);
        //获取文件的md5
        String md5 = SecureUtil.md5(uploadFile);
        //判断数据库是否存在相同记录
        AvatarFiles one = null;
        if (currentUser != null) {
            one = getOneByUserId( currentUser.getUserId());
        }else {return "Token失效";}
        String ip = getIp();
//        url = "http://"+ip+"/file/download/" + fileUUID;
        url = "/file/download/" + fileUUID;
        if (one != null) {
            //删除用户保存在磁盘上的原图片
            String oneUrl = one.getUrl();
            int index = oneUrl.lastIndexOf("/");
            String urlUuid = oneUrl.substring(index + 1);
            File file1 = new File(fileUploadPath + urlUuid);
            file1.delete();

            //由于文件已存在，则更新刚上传的重复文件
            one.setUrl(url);
            fileService.updateById(one);
        } else {
            //数据库若不存在重复文件，则不删除刚才上传的文件
            //存储到数据库
            AvatarFiles files = new AvatarFiles();
            files.setUserId(currentUser.getUserId());
            files.setUrl(url);
            files.setMd5(md5);
            fileService.save(files);
        }
        User user = new User();
        user.setUserId(currentUser.getUserId());
        user.setAvatar(url);
        userService.updateById(user);

        return url;
    }

    /**
     * 下载接口  http://127.0.0.1:3000/files/download/" + fileUUID;
     *
     * @return
     */
    @GetMapping("/download/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        //设置输出流格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();

    }

    public AvatarFiles getOneByUserId( String userId) {
        QueryWrapper<AvatarFiles> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("user_id",userId);
        return  fileService.getOne(fileQueryWrapper);

    }

    //定时器100s执行一次
    @Scheduled(fixedDelay = 100000)
    public String getIp(){
        LinkedHashSet<String> strings = NetUtil.localIpv4s();
        for (String string : strings) {
            if (!string.equals("127.0.0.1") ) {
                String  ip = string+":3000";
                return ip;

            }
        }
        return  "127.0.0.1:3000";
    }


}

