package com.harbor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.harbor.entity.Project;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ProjectMapper extends BaseMapper<Project> {

    @Select("select sum(cost_paid) costPaid, year(project_date) costYear from project group by year(project_date)")
    List<Map<String,Object>> staticProjectCost();

    @Select("select sum(cost_paid) cost_paid, month(project_date) cost_month from project where year(project_date)=#{year} group by month(project_date)")
    List<Map<String,Object>> staticProjectCostByYear(int year);

}
