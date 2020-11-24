package com.harbor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harbor.entity.ProjectDetail;

import java.util.List;

public interface ProjectDetailService extends IService<ProjectDetail> {

    List<ProjectDetail> listByProjectId(int projectId);
}
