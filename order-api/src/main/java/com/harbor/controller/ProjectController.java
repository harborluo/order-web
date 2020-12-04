package com.harbor.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.harbor.entity.Project;
import com.harbor.entity.ProjectDetail;
import com.harbor.entity.ProjectPay;
import com.harbor.module.ResponseResult;
import com.harbor.service.ProjectDetailService;
import com.harbor.service.ProjectPayService;
import com.harbor.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * Created by harbor on 2020/3/28.
 */
@Validated
@RestController
@Slf4j
public class ProjectController {

    @Autowired
    ProjectService service;

    @Autowired
    ProjectDetailService detailService;

    @Autowired
    ProjectPayService payService;

    @GetMapping("/projects")
    public ResponseResult<IPage<Project>> retrieveProject(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") @Max(500) int pageSize,
            @RequestParam(value = "projectFromDate", required = false) String projectFromDate,
            @RequestParam(value = "projectToDate", required = false) String projectToDate,
            @RequestParam(value = "payFromDate", required = false) String payFromDate,
            @RequestParam(value = "payToDate", required = false) String payToDate,
            @RequestParam(value = "serialNo", required = false) String serialNo,
            @RequestParam(value = "isDealDone", required = false) String isDealDone,
            @RequestParam(value = "isValidate", required = false) String isValidate){

        IPage<Project> result = service.queryProjectByPage(page, pageSize, projectFromDate, projectToDate,
                payFromDate, payToDate, serialNo, isValidate, isDealDone);

        return  ResponseResult.ok(result);
    }

    @GetMapping("/project/{id}")
    public ResponseResult<Project> retrieveProjectById(@PathVariable("id") int id){

        Project project = service.getById(id);

        if (project == null) {
            log.error("Project with id {} not found", id);
            return ResponseResult.notFound("Project with id " + id + " not found");
        }

        List<ProjectDetail> details = detailService.listByProjectId(id);
        List<ProjectPay> pays = payService.listByProjectId(id);
        project.setDetails(details);
        project.setPays(pays);

        return  ResponseResult.ok(project);
    }

    @PostMapping("/project")
    public ResponseResult<String> createProject(@RequestBody Project project){
        service.save(project);
        return  ResponseResult.ok("添加成功！");
    }

    @PutMapping("/project")
    public ResponseResult<String> updateProject(@RequestBody Project project){

        service.updateById(project);
        return  ResponseResult.ok("修改成功！");
    }

    @DeleteMapping("/project/{id}")
    public ResponseResult<Void> deleteProject(@PathVariable("id") Integer id) {
        service.removeById(id);

        return ResponseResult.ok();
    }
}
