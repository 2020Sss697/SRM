package com.example.controller;

import com.example.pojo.Quotation;
import com.example.pojo.Result;
import com.example.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotation")
public class QuotationController {
    @Autowired
    private QuotationService quotationService;

    @PostMapping
    public Result add(@RequestBody @Validated Quotation quotation){
        quotationService.add(quotation);
        return Result.success();
    }

    @GetMapping
    public  Result<List<Quotation>> list(){
        List<Quotation> qu = quotationService.list();
        return Result.success(qu);
    }
    @GetMapping("/list")
    public  Result<List<Quotation>> listAll(){
        List<Quotation> all = quotationService.listAll();
        return Result.success(all);
    }

    @GetMapping("/listI")
    public  Result<List<Quotation>> listI(Integer inquiryId){
        List<Quotation> i = quotationService.listI(inquiryId);
        return Result.success(i);
    }

    @GetMapping("/listIi")
    public  Result<List<Quotation>> listII(Integer inquiryId){
        List<Quotation> ii = quotationService.listIi(inquiryId);
        return Result.success(ii);
    }

    @PutMapping
    public Result<String> updateQuotation(@RequestBody @Validated(Quotation.Update.class) Quotation quotation) {
        quotationService.updateQuotation(quotation);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        quotationService.deleteQuotation(id);
        return Result.success();
    }

    @PutMapping("/win")
    public Result win(Integer id) {
        quotationService.win(id, 1);
        return Result.success();
    }

}
