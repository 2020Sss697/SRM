package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.groups.Default;

import java.time.LocalDateTime;

@Data
public class Requisition {
    @NotNull
    private int id;
    private int status;//申请状态(0待审核，1审核通过，2拒绝)
    private int buyerId;//采购员id
    private String buyerName;//采购员名字
    private String materialName;//物料名称
    private int materialNum;//物料数量
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//申请创建时间

    public interface Add extends Default{

    }

    public interface Update extends Default{

    }
}
