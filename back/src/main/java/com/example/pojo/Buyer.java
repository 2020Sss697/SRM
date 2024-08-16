package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Buyer {
    @NotNull
    private int id;
    private String username;//采购员用户名
    @JsonIgnore
    private String password;//采购员密码
    private String name;//采购员名字
    private String phone;//采购员电话
}
