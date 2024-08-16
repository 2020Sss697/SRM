package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Po {
    @NotNull
    private int id;
    private int status;//订单状态(0未开始，1进行中，2已结束)
    private int supplierId;//供应商id
    private String supplierName;//供应商名称
    private int buyerId;//采购员id
    private String materialName;//物料名称
    private int materialNum;//物料数量
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//订单创建时间
    private double price;//价格
    private int trackingNum;//订单物流单号
}
