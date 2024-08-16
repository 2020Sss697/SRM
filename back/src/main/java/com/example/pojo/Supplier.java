package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Supplier {
    @NotNull
    private int id;
    private String username;//供应商用户名
    @JsonIgnore
    private String password;//供应商密码
    private String name;//供应商名称
    private int status;//状态(0注册，1审核中，2试用，3正式)
    private String contacts;//联系人
    private String phone;//联系人电话
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;//注册时间
    @Email
    private String email;//邮箱
    private String address;//地址

}
