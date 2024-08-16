package com.example.controller;

import com.example.pojo.Result;
import com.example.service.ManagerService;
import com.example.utils.JwtUtil;
import com.example.utils.Md5Util;
import com.example.pojo.Manager;
import com.example.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/manager")
@Validated
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result<String> managerLogin(@Pattern(regexp = "^\\S{5,16}$")String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        Manager loginManager = managerService.findByManagerName(username);
        if(loginManager == null){
            return Result.error("用户名错误");
        }

        if (Md5Util.getMD5String(password).equals(loginManager.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginManager.getId());
            claims.put("username",loginManager.getUsername());
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/managerInfo")
    public Result<Manager> managerInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        Manager manager = managerService.findByManagerName(username);
        return Result.success(manager);
    }

}
