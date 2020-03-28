package com.harbor.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harbor.entity.Project;
import com.harbor.mapper.ProjectMapper;
import com.harbor.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by harbor on 2020/3/28.
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public IPage<Project> queryProjectByPage(Page<Project> page, Wrapper<Project> criteria) {
        return baseMapper.selectPage(page, criteria);
    }

    @Override
    public IPage<Project> queryProjectByPage(int page, int pageSize,
                                             String projectFromDate, String projectToDate,
                                             String payFromDate, String payToDate,
                                             String isDealDone) {
        QueryWrapper<Project> entityWrapper = new QueryWrapper<Project>();

        if (!StringUtils.isEmpty(projectFromDate) && ! StringUtils.isEmpty(projectToDate)) {
            entityWrapper.between("project_date", projectFromDate, projectToDate);
        } else if (!StringUtils.isEmpty(projectFromDate) &&  StringUtils.isEmpty(projectToDate)) {
            entityWrapper.ge("project_date", projectFromDate);
        } else if (!StringUtils.isEmpty(projectFromDate) &&  StringUtils.isEmpty(projectToDate)) {
            entityWrapper.le("project_date", projectToDate);
        }

        if (!StringUtils.isEmpty(payFromDate) && ! StringUtils.isEmpty(payToDate)) {
            entityWrapper.apply("exists (select 1 from project_pay pp where project.id = pp.project_id and pp.pay_date between {0} and {1})", payFromDate, payToDate);
        } else if (!StringUtils.isEmpty(payFromDate) &&  StringUtils.isEmpty(payToDate)) {
            entityWrapper.apply("exists (select 1 from project_pay pp where project.id = pp.project_id and pp.pay_date >= {0})", payFromDate);
        } else if (StringUtils.isEmpty(payFromDate) &&  !StringUtils.isEmpty(payToDate)) {
            entityWrapper.apply("exists (select 1 from project_pay pp where project.id = pp.project_id and pp.pay_date <= {0})", payToDate);
        }



        entityWrapper.orderByDesc("project_date");

        int current = page + (page -1) * pageSize;

        return baseMapper.selectPage(new Page<Project>(current, pageSize), entityWrapper);
    }
}
