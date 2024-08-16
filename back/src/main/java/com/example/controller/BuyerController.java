package com.example.controller;

import com.example.pojo.Buyer;
import com.example.pojo.Result;
import com.example.service.BuyerService;
import com.example.utils.JwtUtil;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/buyer")
@Validated
public class BuyerController {

    @Autowired
    private BuyerService buyerService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/login")
    public Result<String> buyerLogin(@Pattern(regexp = "^\\S{5,16}$")String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        Buyer loginBuyer = buyerService.findByBuyerName(username);
        if(loginBuyer == null){
            return Result.error("用户名错误");
        }

        if (Md5Util.getMD5String(password).equals(loginBuyer.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginBuyer.getId());
            claims.put("username",loginBuyer.getUsername());
            claims.put("name",loginBuyer.getName());
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/buyerInfo")
    public Result<Buyer> buyerInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        Buyer buyer = buyerService.findByBuyerName(username);
        return Result.success(buyer);
    }

    @PutMapping("/update")
    public Result updateBuyer(@RequestBody @Validated Buyer buyer){
        buyerService.updateBuyer(buyer);
        return Result.success();
    }
}
