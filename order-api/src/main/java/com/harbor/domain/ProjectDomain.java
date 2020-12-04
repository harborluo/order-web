package com.harbor.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.harbor.config.DateJsonDeserialize;
import com.harbor.config.DateJsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProjectDomain {

    private int id;

    @NotNull(message = "project name can't be empty.")
    private String name;


    @NotNull(message = "project date can't be empty.")
    @JsonDeserialize(using = DateJsonDeserialize.class)
    @JsonSerialize(using = DateJsonSerialize.class)
    private Date projectDate;

    private BigDecimal processCost;

    private BigDecimal materialCost;

    @NotNull(message = "project cost can't be empty")
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
