package com.harbor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harbor.entity.ProjectDetail;
import com.harbor.entity.ProjectPay;
import com.harbor.mapper.ProjectDetailMapper;
import com.harbor.mapper.ProjectPayMapper;
import com.harbor.service.ProjectDetailService;
import com.harbor.service.ProjectPayService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectPayServiceImpl extends ServiceImpl<ProjectPayMapper, ProjectPay> implements ProjectPayService {

    @Override
    public List<ProjectPay> listByProjectId(int projectId) {
        QueryWrapper<ProjectPay> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("project_id", projectId);
        queryWrapper.select("*");
        return this.baseMapper.selectList(queryWrapper);
    }

}
