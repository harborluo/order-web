package com.harbor.controller;

import com.harbor.entity.Project;
import com.harbor.module.ResponseResult;
import com.harbor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by harbor on 2020/3/28.
 */
@RestController
public class ProjectController {

    @Autowired
    ProjectService service;

    @GetMapping("/projects/")
    public ResponseResult<List<Project>> retrieveProject(){

        List<Project> list = service.list();

        return  ResponseResult.ok(list);
    }
}
