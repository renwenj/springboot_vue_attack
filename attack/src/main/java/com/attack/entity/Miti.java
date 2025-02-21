package com.attack.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@TableName("miti")
public class Miti {
    @TableId
    @JsonProperty("no")
    private String no;

    @JsonProperty("e")
    private String e;

    @JsonProperty("cd")
    private String cd;
}

