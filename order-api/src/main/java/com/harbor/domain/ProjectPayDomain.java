package com.harbor.domain;

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

@Data
public class ProjectPayDomain {

    private int id;

//    private int projectId;

    private BigDecimal pay;

    @JsonDeserialize(using = DateJsonDeserialize.class)
    @JsonSerialize(using = DateJsonSerialize.class)
    private Date payDate;

    private String type;

    private String payee;

    private String payNo;

}
