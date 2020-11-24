package com.harbor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "project_detail")
public class ProjectDetail {

    @TableId("id")
    private int id;

    @TableField(value = "project_id")
    private int projectId;

    @TableField(value = "width")
    private BigDecimal width;

    @TableField(value = "height")
    private BigDecimal height;

    @TableField(value = "material")
    private String material;

    @TableField(value = "price")
    private BigDecimal price;

    @TableField(value = "quantity")
    private int quantity;

    @TableField(value = "note")
    private String note;

}
