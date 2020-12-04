package com.harbor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harbor.entity.ProjectDetail;
import com.harbor.entity.ProjectPay;

import java.util.List;

public interface ProjectPayService extends IService<ProjectPay> {

    List<ProjectPay> listByProjectId(int projectId);

    void removeByProjectIds(List<Integer> projectIds);

}
