package com.example.controller;

import com.example.pojo.Performance;
import com.example.pojo.Result;
import com.example.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performance")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @PostMapping
    public Result add(@RequestBody @Validated Performance performance){
        performanceService.add(performance);
        return Result.success();
    }

    @GetMapping()
    public  Result<List<Performance>> list(){
        List<Performance> lp = performanceService.list();
        return Result.success(lp);
    }

    @PutMapping
    public Result<String> updatePerformance(@RequestBody @Validated Performance performance) {
        performanceService.updatePerformance(performance);
        return Result.success();
    }

    @GetMapping("/all")
    public  Result<List<Performance>> listAll(){
        List<Performance> all = performanceService.listAll();
        return Result.success(all);
    }

    @GetMapping("/myPf")
    public  Result<List<Performance>> listMy(){
        List<Performance> my = performanceService.listMy();
        return Result.success(my);
    }
}
