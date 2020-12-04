package com.harbor.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjectDetailDomain {


    private int id;

//    private int projectId;

    private BigDecimal width;

    private BigDecimal height;

    private String material;

    private BigDecimal price;

    private int quantity;

    private String note;

}
