package com.harbor.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.harbor.config.DateJsonDeserialize;
import com.harbor.config.DateJsonSerialize;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Validated
public class ProjectPayDomain {

    private int id;

//    private int projectId;

    @NotNull(message = "支付金额不能为空.")
    private BigDecimal pay;

    @NotNull(message = "支付日期不能为空.")
    @JsonDeserialize(using = DateJsonDeserialize.class)
    @JsonSerialize(using = DateJsonSerialize.class)
    private Date payDate;

    @NotNull(message = "用途不能为空.")
    private String type;

    private String payee;

    @NotNull(message = "收款单号不能为空.")
    private String payNo;

    public BigDecimal getPay() {
        return pay == null ? new BigDecimal(0) : pay;
    }
}
