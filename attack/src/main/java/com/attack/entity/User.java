package com.attack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId
    @JsonProperty("id")
    private int id;

    @JsonProperty("no")
    private String no;

    @JsonProperty("age")
    private String age;

    @JsonProperty("name")
    private String name;
    //public String getName() {
        //return this.name;
    //}

    @JsonProperty("password")
    private String password;

    @JsonProperty("sex")
    private int sex;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("roleId")
    private int roleId;

    @JsonProperty("isValid")
    private String isValid;
}
