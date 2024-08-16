package com.example.controller;


import com.example.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Pattern;
import com.example.pojo.Result;
import com.example.pojo.Supplier;
import com.example.utils.JwtUtil;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/supplier")
@Validated
public class SupplierController {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result supplierRegister(@Pattern(regexp = "^\\S{5,16}$")String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        Supplier s = supplierService.findBySupplierName(username);
        if (s == null) {
            supplierService.supplierRegister(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$")String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        Supplier loginSupplier = supplierService.findBySupplierName(username);

        if(loginSupplier==null){
            return Result.error("用户名错误");
        }


        if (Md5Util.getMD5String(password).equals(loginSupplier.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginSupplier.getId());
            claims.put("username",loginSupplier.getUsername());
            claims.put("name",loginSupplier.getName());
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/supplierInfo")
    public Result<Supplier> supplierInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        Supplier supplier = supplierService.findBySupplierName(username);
        return Result.success(supplier);
    }

    @GetMapping()
    public  Result<List<Supplier>> list(){
        List<Supplier> lp = supplierService.list();
        return Result.success(lp);
    }

    @GetMapping("/status")
    public Result getSupplierStatus(){
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        Supplier supplier = supplierService.getSupplierStatus(id);
        return Result.success(supplier);
    }

    @GetMapping("/wait")
    public  Result<List<Supplier>> listWait(){
        List<Supplier> wait = supplierService.listWait();
        return Result.success(wait);
    }

    @PutMapping("/addScore")
    public Result addScore(@RequestBody Supplier supplier){
        supplierService.addScore(supplier);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateSupplier(@RequestBody @Validated Supplier supplier){
        supplierService.updateSupplier(supplier);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }


        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        Supplier loginSupplier = supplierService.findBySupplierName(username);
        if(!loginSupplier.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写错误");

        }

        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的密码不一致");
        }

        supplierService.updateSupplierPwd(newPwd);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
    @PutMapping("/access")
    public Result accessRequisition(Integer id) {
        supplierService.access(id, 1);
        return Result.success();
    }
}

