package com.harbor.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Validated
public class ProjectDetailDomain {


    private int id;

//    private int projectId;

    private BigDecimal width;

    private BigDecimal height;

    @NotNull(message = "材料不能为空.")
    private String material;

    private BigDecimal price;

    private int quantity;

    private String note;

}
