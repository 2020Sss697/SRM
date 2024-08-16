package com.example.controller;

import com.example.pojo.Result;
import com.example.pojo.Po;
import com.example.service.PoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/po")
public class PoController {
    @Autowired
    private PoService poService;

    @PostMapping
    public Result add(@RequestBody @Validated Po po){
        poService.add(po);
        return Result.success();
    }

    @PutMapping("/addTrackingNum")
    public Result addTrackingNum(@RequestBody Po po){
        poService.addTrackingNum(po);
        return Result.success();
    }

    @GetMapping
    public  Result<List<Po>> list(){
        List<Po> p = poService.list();
        return Result.success(p);
    }

    @GetMapping("/myPo")
    public  Result<List<Po>> myPo(){
        List<Po> my = poService.myPo();
        return Result.success(my);
    }

    @PutMapping("/approve")
    public Result approvePo(Integer id) {
        poService.approve(id, 1);
        return Result.success();
    }
    @PutMapping("/reject")
    public Result rejectPo(Integer id) {
        poService.reject(id, 2);
        return Result.success();
    }
    @PutMapping("/deliver")
    public Result deliverPo(Integer id) {
        poService.deliver(id, 3);
        return Result.success();
    }
    @PutMapping("/receive")
    public Result receivePo(Integer id) {
        poService.receive(id, 4);
        return Result.success();
    }
}
