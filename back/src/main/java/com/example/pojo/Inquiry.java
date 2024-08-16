package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Inquiry {
    @NotNull
    private int id;
    private int buyerId;//采购员id
    private String materialName;//物料名称
    private int materialNum;//物料数量
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;//询价开始时间


    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
