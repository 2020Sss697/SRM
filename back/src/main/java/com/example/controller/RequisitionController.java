package com.example.controller;

import com.example.pojo.Requisition;
import com.example.pojo.Result;
import com.example.service.RequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/requisition")
public class RequisitionController {

    @Autowired
    private RequisitionService requisitionService;

    @PostMapping
    public Result add(@RequestBody @Validated(Requisition.Add.class) Requisition requisition){
        requisitionService.add(requisition);
        return Result.success();
    }

    @GetMapping
    public Result <List<Requisition>> list(){
        List<Requisition> re = requisitionService.list();
        return Result.success(re);
    }

    @PutMapping
    public Result<String> update(@RequestBody @Validated(Requisition.Update.class) Requisition requisition) {
        requisitionService.update(requisition);
        return Result.success();
    }

    @GetMapping("/all")
    public  Result<List<Requisition>> listAll(){
        List<Requisition> all = requisitionService.listAll();
        return Result.success(all);
    }

    @DeleteMapping
    public Result delete(Integer id){
        requisitionService.deleteById(id);
        return Result.success();
    }

    @PutMapping("/approve")
    public Result approveRequisition(Integer id) {
        requisitionService.approve(id, 1);
        return Result.success();
    }
    @PutMapping("/reject")
    public Result rejectRequisition(Integer id) {
        requisitionService.reject(id, 2);
        return Result.success();
    }
}
