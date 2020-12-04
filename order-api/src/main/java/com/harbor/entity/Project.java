package com.harbor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.harbor.config.DateJsonDeserialize;
import com.harbor.config.DateJsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName(value = "project")
public class Project {

    @TableId("id")
    private int id;

    @TableField(value = "name")
    private String name;

    @JsonDeserialize(using = DateJsonDeserialize.class)
    @JsonSerialize(using = DateJsonSerialize.class)
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

//    @TableField(exist = false)
//    private List<ProjectDetail> details;
//
//    @TableField(exist = false)
//    private List<ProjectPay> pays;

}
