package com.example.controller;

import com.example.pojo.Inquiry;
import com.example.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.pojo.Result;

import java.util.List;

@RestController
@RequestMapping("/inquiry")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @PostMapping
    public Result add(@RequestBody @Validated Inquiry inquiry){
        inquiryService.add(inquiry);
        return Result.success();
    }

    @GetMapping
    public  Result<List<Inquiry>> list(){
        List<Inquiry> iq = inquiryService.list();
        return Result.success(iq);
    }

    @GetMapping("/list")
    public  Result<List<Inquiry>> listAll(){
        List<Inquiry> all = inquiryService.listAll();
        return Result.success(all);
    }
    @DeleteMapping
    public Result delete(Integer id){
        inquiryService.deleteInquiry(id);
        return Result.success();
    }
}
