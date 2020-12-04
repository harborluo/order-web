package com.harbor.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbor.entity.Project;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created by harbor on 2020/3/28.
 */
public interface ProjectService extends IService<Project> {

    IPage<Project> queryProjectByPage(Page<Project> page, Wrapper<Project> criteria);

    IPage<Project> queryProjectByPage(int page, int pageSize,
                                      String projectFromDate, String projectToDate,
                                      String payFromDate, String payToDate,
                                      String serialNo,
                                      String isValidate,
                                      String isDealDone);

    void removeByIds(List<Integer> ids);
}
