package com.harbor.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.harbor.entity.Project;
import com.harbor.module.ResponseResult;
import com.harbor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;

/**
 * Created by harbor on 2020/3/28.
 */
@RestController
public class ProjectController {

    @Autowired
    ProjectService service;

    @GetMapping("/projects")
    public ResponseResult<IPage<Project>> retrieveProject(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") @Max(500) int pageSize,
            @RequestParam(value = "projectFromDate", required = false) String projectFromDate,
            @RequestParam(value = "projectToDate", required = false) String projectToDate,
            @RequestParam(value = "payFromDate", required = false) String payFromDate,
            @RequestParam(value = "payToDate", required = false) String payToDate,
            @RequestParam(value = "isDealDone", required = false) String isDealDone){


        IPage<Project> result = service.queryProjectByPage(page, pageSize, projectFromDate, projectToDate,
                payFromDate, payToDate, isDealDone);

        return  ResponseResult.ok(result);
    }

    @GetMapping("/projects/{id}")
    public ResponseResult<Project> retrieveProjectById(@PathVariable("id") int id){

        Project project = service.getById(id);

        return  ResponseResult.ok(project);
    }
}
