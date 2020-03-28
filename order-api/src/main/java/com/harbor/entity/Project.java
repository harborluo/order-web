package com.harbor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "project")
public class Project {

    @TableField(value = "id")
    private int id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "project_date")
    private Date projectDate;

    @TableField(value = "process_cost")
    private BigDecimal processCost;

    @TableField(value = "material_cost")
    private BigDecimal materialCost;

    @TableField(value = "cost")
    private BigDecimal cost;

    @TableField(value = "serrial_no")
    private String serialNo;

    @TableField(value = "cost_paid")
    private BigDecimal costPaid;

    @TableField(value = "begin_date")
    private Date beginDate;

    @TableField(value = "finish_date")
    private Date finishDate;

    @TableField(value = "note")
    private String note;

    @TableField(value = "client_name")
    private String clientName;

    @TableField(value = "client_phone")
    private String clientPhone;

    @TableField(value = "client_address")
    private String clientAddress;

}
