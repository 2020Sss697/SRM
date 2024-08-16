package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Manager {
    @NotNull
    private int id;
    private String username;//经理用户名
    @JsonIgnore
    private String password;//经理密码
    private String name;
}
