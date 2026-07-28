package com.theona.controller;

import com.theona.pojo.Result;
import com.theona.pojo.User;
import com.theona.service.UserService;
import com.theona.utils.JwtUtil;
import com.theona.utils.Md5Util;
import com.theona.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password){
        User user = userService.findByUserName(username);
        // 没有被占用
        if(user==null){
            // 注册
            userService.register(username,password);
            return Result.success();
        }else {
            // 占用
            return Result.error("用户名已被占用");
        }
    }

    // 登录
    @PostMapping("/login")
    public Result<String> longin(@Pattern(regexp = "^\\S{5,16}$") String username,
                                 @Pattern(regexp = "^\\S{5,16}$") String password){
        User user = userService.findByUserName(username);

        // 用户是否存在
        if(user == null){
            return Result.error("用户名不存在");
        }

        // 判断密码是否正确
        String Md5psw = Md5Util.encrypt(password);

        //登录成功
        if(Md5psw.equals(user.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String Token = JwtUtil.genToken(claims);

            // 把Token存储到Redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(Token,Token,12, TimeUnit.HOURS);

            return Result.success(Token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token){
//        Map<String,Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");

        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUserName(username);

        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader(name = "Authorization")String Token){
        // 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("密码不能为空");
        }

        // 原密码是否正确
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);

        if (!user.getPassword().equals(Md5Util.encrypt(oldPwd))){
            return Result.error("原密码错误");
        }

        // 两次新密码是否一致
        if (!newPwd.equals(rePwd)){
            return Result.error("新密码须一致");
        }

        // 调用service修改密码
        userService.updatePwd(newPwd);

        // 删除Redis中对应的Token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(Token);

        return Result.success();
    }
}
