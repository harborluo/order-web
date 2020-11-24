package com.harbor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "project_pay")
public class ProjectPay {

    @TableId("id")
    private int id;

    @TableField(value = "project_id")
    private int projectId;

    @TableField(value = "pay")
    private BigDecimal pay;

    @TableField(value = "pay_date")
    private Date payDate;

    @TableField(value = "type")
    private String type;

    @TableField(value = "pay_ee")
    private String payee;

    @TableField(value = "pay_no")
    private String payNo;

}
