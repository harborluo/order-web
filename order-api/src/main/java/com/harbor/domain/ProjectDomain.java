package com.harbor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.harbor.config.DateJsonDeserialize;
import com.harbor.config.DateJsonSerialize;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Validated
public class ProjectDomain {

    private int id;

    @NotNull(message = "工程名称不为能空.")
    private String name;


    @NotNull(message = "工程日期不为能空.")
    @JsonDeserialize(using = DateJsonDeserialize.class)
    @JsonSerialize(using = DateJsonSerialize.class)
    private Date projectDate;

    private BigDecimal processCost;

    private BigDecimal materialCost;

    @NotNull(message = "工程总价不为能空.")
    private BigDecimal cost;

    private String serialNo;

    private BigDecimal costPaid;

    private Date beginDate;

    private Date finishDate;

    private String note;

    private String clientName;

    private String clientPhone;

    private String clientAddress;

    private List<ProjectDetailDomain> details;

    private List<ProjectPayDomain> pays;

}
