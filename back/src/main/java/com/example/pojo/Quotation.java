package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

@Data
public class Quotation {
    @NotNull
    private int id;
    private int supplierId;//供应商id
    private int inquiryId;//询价单id
    private String supplierName;//供应商名称
    private String materialName;//物料名称
    private int materialNum;//物料数量
    private double price;//报价
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String deliverTime;//交付时间
    private int status;//状态

    public interface Update extends Default {

    }
}
