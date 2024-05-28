package com.lf.losefat.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.losefat.common.Constants;
import com.lf.losefat.common.Result;
import com.lf.losefat.controller.dto.EditPwd;
import com.lf.losefat.controller.dto.PhoneLogin;
import com.lf.losefat.controller.dto.SmsLogin;
import com.lf.losefat.controller.dto.WxLogin;
import com.lf.losefat.entity.AvatarFiles;
import com.lf.losefat.entity.User;
import com.lf.losefat.service.IFileService;
import com.lf.losefat.service.IUserService;
import com.lf.losefat.utils.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author 小肚
 * @since 2024-03-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IFileService fileService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate; //redis

    @PostMapping("/wx_login")
    public Result getUserOpenId(@RequestBody WxLogin wxLogin) throws InvalidAlgorithmParameterException, UnsupportedEncodingException {
        String wxLoginUrl = "https://api.weixin.qq.com/sns/jscode2session";
        //接口参数
        String param = "appid=wx64df49989c043d5c&secret=8916c0ab9804283235c0fc553b8db669&js_code=" + wxLogin.getJs_code() + "&grant_type=authorization_code";
        //调用获取session_key接口 请求方式get
        String jsonString = GetPostUntil.sendGet(wxLoginUrl, param);
        //因为json字符串是大括号包围，所以用JSONObject解析
        JSONObject json = new JSONObject(jsonString);
        //json解析session_key值
        String session_key = json.getStr("session_key");
        String openid = json.getStr("openid");
        String phoneNumber = this.decodeUserInfo(wxLogin.getEncryptedData(), wxLogin.getIv(), session_key);
        if (StrUtil.isNotBlank(phoneNumber)) {
            //异步执行
            CompletableFuture.runAsync(() -> {
                QueryWrapper<User> userinfoQueryWrapper = new QueryWrapper<>();
                userinfoQueryWrapper.eq("phone", phoneNumber);
                User one = userService.getOne(userinfoQueryWrapper);
                if (one == null) {
                    User UserInfo = new User();
                    UserInfo.setOpenId(generateUserId());
                    UserInfo.setUserPhone(phoneNumber);
                    UserInfo.setAvatar(wxLogin.getAvatar());
                    UserInfo.setUserName(wxLogin.getName());
                    UserInfo.setOpenId(openid);
                    userService.save(UserInfo);
                } else {
                    one.setUserName(wxLogin.getName());
                    one.setUserPhone(phoneNumber);
                    one.setOpenId(openid);
                    userService.updateById(one);
                }


            });

            String token = TokenUtils.genToken(openid, phoneNumber);
            HashMap<String, String> data = new HashMap<>();
            data.put("phoneNumber", phoneNumber);
            data.put("token", token);
            return Result.success(data);
        }
        //返回给前端
        return Result.error(Constants.CODE_400, "系统错误!");
    }

    /**
     * 解密小程序用户敏感数据
     *
     * @param encryptedData 明文
     * @param iv            加密算法的初始向量
     * @param sessionKey    用户秘钥
     * @return
     */
    public String decodeUserInfo(String encryptedData,
                                 String iv,
                                 String sessionKey
    ) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, JSONException {
        //AESUtils微信获取手机号解密工具类
        AESUtils aes = new AESUtils();
        //调用AESUtils工具类decrypt方法解密获取json串
        byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
        //判断返回参数是否为空
        if (null != resultByte && resultByte.length > 0) {
            String jsons = new String(resultByte, "UTF-8");
            JSONObject json = new JSONObject(jsons);
            return json.getStr("phoneNumber");
        }
        return null;
    }


    /**
     * @return 用户id
     * 随机生成用户id
     */
    public static String generateUserId() {
        // 生成一个随机的UUID
        UUID uuid = UUID.randomUUID();
        // 将UUID转换为字符串格式
        return uuid.toString();
    }


    /**
     * @param phoneLogin 密码登录
     * @return User 用户信息
     */
    @PostMapping("/password_login")
    public Result loginByPassword(@RequestBody PhoneLogin phoneLogin) {
        String md5Pwd = Md5Util.getMD5String(phoneLogin.getPwd());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_phone", phoneLogin.getPhone());
        userQueryWrapper.eq("user_password", md5Pwd);
        User one = userService.getOne(userQueryWrapper);
        if (BeanUtil.isNotEmpty(one)) {
            String token = TokenUtils.genToken(one.getUserId(), one.getUserPhone());
            one.setToken(token);
            return Result.success(one);
        }

        return Result.error(Constants.CODE_400, "用户名或密码不存在！");
    }


    /**
     * 发送验证码
     *
     * @param phone 接收验证码的手机号
     * @return 验证码
     * @throws Exception
     */
    @PostMapping("/getCode")
    public Result getCode(String phone) throws Exception {
        String KEY = phone;
        Integer code = new Random().nextInt(9000) + 1000;

        stringRedisTemplate.opsForValue().set(KEY, String.valueOf(code), Duration.ofMinutes(5));
        SendSmsResponseBody resBody = new Sample().sendSsm(phone, code);
        if (resBody.code.equals("OK")) {
            return Result.success();
        }
        return Result.error(Constants.CODE_400, resBody.getMessage());

    }

    /**
     * 验证码登录
     *
     * @param smsLogin 手机号和验证码
     * @return User 用户信息
     */
    @PostMapping("/sms_login")
    public Result smsLogin(@RequestBody SmsLogin smsLogin) {
        String code = stringRedisTemplate.opsForValue().get(smsLogin.getPhone());
        if (code != null) {
            if (smsLogin.getCode().toString().equals(code)) {
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("user_phone", smsLogin.getPhone());
                stringRedisTemplate.delete(smsLogin.getPhone());
                User user = userService.getOne(userQueryWrapper);
                if (user != null) {
                    String token = TokenUtils.genToken(user.getUserId(), smsLogin.getPhone());
                    user.setToken(token);
                    return Result.success(user);
                }
                User newUser = new User();
                String userId = generateUserId();
                String token = TokenUtils.genToken(userId, smsLogin.getPhone());
                newUser.setUserPhone(smsLogin.getPhone());
                newUser.setUserPassword(Md5Util.getMD5String(userId.substring(0, 8)));
                newUser.setUserId(userId);
                newUser.setUserName(userId.substring(0, 8) + "用户");
                newUser.setToken(token);
                userService.save(newUser);
                return Result.success(newUser);
            }
            return Result.error(Constants.CODE_400, "验证码错误！");
        }
        return Result.error(Constants.CODE_400, "验证码错误！");
    }

    /**
     * 修改用户名
     *
     * @param editUserName 修改的用户名
     * @return 用户名信息
     */
    @PostMapping("/updateUserName")
    public Result updateUserName(String editUserName) {
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null) {
            currentUser.setUserName(editUserName);
            userService.updateById(currentUser);
            return Result.success(currentUser);

        }
        return Result.error(Constants.CODE_401, "Token失效！");

    }

    /**
     * 更改密码
     *
     * @param editPwd
     * @return
     */
    @PostMapping("/updateUserPwd")
    public Result updateUserPwd(@RequestBody EditPwd editPwd) {
        User currentUser = TokenUtils.getCurrentUser();
        boolean b = false;
        if (currentUser != null) {
            b = Md5Util.checkPassword(editPwd.getNewPwd(), currentUser.getUserPassword());
            if (b) {
                return Result.error(Constants.CODE_400, "新旧密码不能一致！");
            }
            currentUser.setUserPassword(Md5Util.getMD5String(editPwd.getNewPwd()));
            userService.updateById(currentUser);
            return Result.success(currentUser);
        }
        return Result.error(Constants.CODE_400, "token失效！");

    }


    /**
     * 保存用户数据
     *
     * @param user
     * @return
     */
    @PostMapping("/saveUpdate")
    public Result saveUpdate(@RequestBody(required = false) User user) {
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null) {
            //自定义copy忽略的字段
            CopyOptions options = CopyOptions.create()
                    .setIgnoreProperties("userId", "openId", "avatar", "userPassword", "token");
            BeanUtil.copyProperties(user, currentUser, options);
            userService.updateById(currentUser);
            String token = TokenUtils.genToken(currentUser.getUserId(), currentUser.getUserPhone());
            currentUser.setToken(token);
            return Result.success(currentUser);
        }
        return Result.error(Constants.CODE_401, "Token失效！");
    }


    /**
     * 更换手机号之前验证现手机号
     *
     * @param smsLogin
     * @return
     */
    @PostMapping("/verifyPhone")
    public Result verifyPhone(@RequestBody SmsLogin smsLogin) {
        String code = stringRedisTemplate.opsForValue().get(smsLogin.getPhone());
        if (code != null) {
            if (Integer.valueOf(code).equals(smsLogin.getCode())) {
                stringRedisTemplate.delete(smsLogin.getPhone());
                return Result.success();
            }
            return Result.error(Constants.CODE_400, "验证码错误！");
        }
        return Result.error(Constants.CODE_400, "验证码错误 ！");

    }

    /**
     * 更改关联手机
     *
     * @param smsLogin
     * @return
     */
    @PostMapping("/updatePhone")
    public Result updatePhone(@RequestBody SmsLogin smsLogin) {
        User currentUser = TokenUtils.getCurrentUser();
        String code = stringRedisTemplate.opsForValue().get(smsLogin.getPhone());
        if (code != null) {
            if (Integer.valueOf(code).equals(smsLogin.getCode())) {
                stringRedisTemplate.delete(smsLogin.getPhone());
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("user_phone", smsLogin.getPhone());
                User one = userService.getOne(userQueryWrapper);
                if (one != null) {
                    return Result.error(Constants.CODE_400, "手机号已绑定其他账户！");
                }

                currentUser.setUserPhone(smsLogin.getPhone());
                userService.updateById(currentUser);
                String token = TokenUtils.genToken(currentUser.getUserId(), currentUser.getUserPhone());
                currentUser.setToken(token);
                return Result.success(currentUser);
            }
            return Result.error(Constants.CODE_400, "验证码错误！");
        }
        return Result.error(Constants.CODE_400, "验证码错误！");


    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @PostMapping("/getUser")
    public Result getUser() {
        return Result.success(TokenUtils.getCurrentUser());
    }


    /**
     * 注销用户
     *
     * @return
     */
    @PostMapping("/deleteUser")
    public Result deleteUser() {
        User currentUser = TokenUtils.getCurrentUser();
        QueryWrapper<AvatarFiles> filesQueryWrapper = new QueryWrapper<>();
        filesQueryWrapper.eq("url", currentUser.getAvatar());
        fileService.remove(filesQueryWrapper);
        return userService.removeById(currentUser.getUserId()) ? Result.success() : Result.error(Constants.CODE_400, "删除失败！");
    }
}


