package com.harbor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harbor.entity.Project;
import com.harbor.entity.ProjectDetail;
import com.harbor.mapper.ProjectDetailMapper;
import com.harbor.service.ProjectDetailService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectDetailServiceImpl extends ServiceImpl<ProjectDetailMapper, ProjectDetail> implements ProjectDetailService {


    @Override
    public List<ProjectDetail> listByProjectId(int projectId) {
        QueryWrapper<ProjectDetail> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("project_id", projectId);
        queryWrapper.eq("project_id", projectId);
        queryWrapper.select("*");
        return this.baseMapper.selectList(queryWrapper);
    }
}
