package com.attack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("tech")
public class Tech {
    @TableId
    @JsonProperty("no")
    private String no;

    @JsonProperty("ca0")
    private String ca0;

    @JsonProperty("ca1")
    private String ca1;
    //public String getName() {
    //return this.name;
    //}

    @JsonProperty("pa0")
    private String pa0;

    @JsonProperty("pa1")
    private String pa1;

    @JsonProperty("r")
    private String r;
}
