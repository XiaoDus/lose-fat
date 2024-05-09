package com.lf.losefat.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.losefat.common.Constants;
import com.lf.losefat.common.Result;
import com.lf.losefat.entity.User;
import com.lf.losefat.entity.UserWeightData;
import com.lf.losefat.service.IUserService;
import com.lf.losefat.service.IUserWeightDataService;
import com.lf.losefat.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 体重记录
 * </p>
 *
 * @author 小肚
 * @since 2024-03-28
 */
@RestController
@RequestMapping("/user_weight_data")
public class UserWeightDataController {
    @Autowired
    private IUserWeightDataService userWeightDataService;

    @Autowired
    private IUserService userService;


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param KEY redis中的key （userId + Weight）
     */
    public void deleteRedis(String KEY) {
        redisTemplate.delete(KEY);
    }

    /**
     * @return 体重记录列表
     */
    @GetMapping
    public Result getWeight() {
        User currentUser = TokenUtils.getCurrentUser();

        if (currentUser != null) {
            ArrayList<HashMap<String, String>> userListData = new ArrayList<>();
            String userId = currentUser.getUserId();
            //获取redis中的数据
            Object WeightList = redisTemplate.opsForValue().get(userId + "WeightList");
            if (ObjectUtil.isEmpty(WeightList)) {
                //redis中为空则查询数据库
                QueryWrapper<UserWeightData> userWeightDataQueryWrapper = new QueryWrapper<>();
                userWeightDataQueryWrapper.eq("user_id", userId);
                userWeightDataQueryWrapper.orderByAsc("time");
                List<UserWeightData> list = userWeightDataService.list(userWeightDataQueryWrapper);

                ArrayList<HashMap<String, String>> finalUserListData = new ArrayList<>();
                list.forEach(res -> {
                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("xAxisData", String.valueOf(res.getTime()));
                    userMap.put("seriesData", String.valueOf(res.getData()));
                    finalUserListData.add(userMap);
                });
                //保存到redis中
                redisTemplate.opsForValue().set(userId + "WeightList", finalUserListData, Duration.ofMinutes(30));
                return Result.success(finalUserListData);
            } else {
                //返回中redis中查询到的数据
                return Result.success((ArrayList<HashMap<String, String>>) WeightList);
            }

        }

        return Result.error(Constants.CODE_401, "Token失效！");
    }

    /**
     * @param userWeightData 今日体重
     * @return
     */
    @PostMapping
    public Result setWeight(@RequestBody(required = false) UserWeightData userWeightData) {
        String today = DateUtil.today();
        User currentUser = TokenUtils.getCurrentUser();
        QueryWrapper<UserWeightData> userWeightDataQueryWrapper = new QueryWrapper<>();
        userWeightDataQueryWrapper.eq("time", today);
        userWeightDataQueryWrapper.eq("user_id", currentUser.getUserId());
        UserWeightData one = userWeightDataService.getOne(userWeightDataQueryWrapper);
        if (currentUser!=null) {
            if (one != null) { //数据库中存在今天的数据
                one.setData(userWeightData.getData());
                userWeightDataService.updateById(one);
                deleteRedis(currentUser.getUserId() + "WeightList");
            } else { //用户存在,今天数据不存在时
                userWeightData.setUserId(currentUser.getUserId());
                userWeightData.setTime(LocalDate.parse(today));
                userWeightDataService.saveOrUpdate(userWeightData);
                deleteRedis(currentUser.getUserId() + "WeightList");
            }
            //更新用户资料
            currentUser.setUserWeight(userWeightData.getData()+"斤");
            userService.updateById(currentUser);
            return Result.success();
       }

        return Result.error(Constants.CODE_401, "Token失效！");
    }

public void updataUser(){

}
    /**
     * 添加体重
     * @param userWeightData
     * @return
     */
    @PostMapping("/add_weight")
    public Result addWeight(@RequestBody(required = false) UserWeightData userWeightData){
        User currentUser = TokenUtils.getCurrentUser();
        userWeightData.setUserId(currentUser.getUserId());
        //查找是否存在该时间的数据
        QueryWrapper<UserWeightData> userWeightDataQueryWrapper = new QueryWrapper<>();
        userWeightDataQueryWrapper.eq("user_id",userWeightData.getUserId());
        userWeightDataQueryWrapper.eq("time",userWeightData.getTime());
        UserWeightData one = userWeightDataService.getOne(userWeightDataQueryWrapper);
        if(one!=null){ //如果存在数据则设置id并更新
            userWeightData.setId(one.getId());
        }
        deleteRedis(currentUser.getUserId() + "WeightList");
        userWeightDataService.saveOrUpdate(userWeightData);
        //更新用户资料
        currentUser.setUserWeight(userWeightData.getData()+"斤");
        userService.updateById(currentUser);
        return Result.success();
    }
    /**
     * 删除指定数据
     * @param time
     * @return
     */
    @GetMapping("/delete")
    public Result deleteWeight(String time){
        User currentUser = TokenUtils.getCurrentUser();
        QueryWrapper<UserWeightData> userWeightDataQueryWrapper = new QueryWrapper<>();
        userWeightDataQueryWrapper.eq("user_id",currentUser.getUserId());
        userWeightDataQueryWrapper.eq("time", time);
        boolean b = userWeightDataService.remove(userWeightDataQueryWrapper);
        if (b) {
            deleteRedis(currentUser.getUserId() + "WeightList");
            return Result.success();
        }
       return Result.error();

    }


}

