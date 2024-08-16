package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Performance {
    @NotNull
    private int id;
    private int supplierId;//供应商id
    private String supplierName;//供应商名称
    private int status;//考核状态(0未开启，1考核中，2考核结束)
    private int buyerId;//采购员id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeLimit;//考核时间
    private int quality;//质量
    private int deliver;//交付
    private int service;//服务
    private double score;//考核评分
    private String remarks;//备注
}
