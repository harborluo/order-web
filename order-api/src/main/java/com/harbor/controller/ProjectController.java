package com.harbor.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;

import com.harbor.domain.ProjectDetailDomain;
import com.harbor.domain.ProjectDomain;
import com.harbor.domain.ProjectPayDomain;
import com.harbor.entity.Project;
import com.harbor.entity.ProjectDetail;
import com.harbor.entity.ProjectPay;
import com.harbor.module.ResponseResult;
import com.harbor.service.ProjectDetailService;
import com.harbor.service.ProjectPayService;
import com.harbor.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.*;

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
    public ResponseEntity retrieveProject(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") @Max(500) int pageSize,
            @RequestParam(value = "projectFromDate", required = false) String projectFromDate,
            @RequestParam(value = "projectToDate", required = false) String projectToDate,
            @RequestParam(value = "payFromDate", required = false) String payFromDate,
            @RequestParam(value = "payToDate", required = false) String payToDate,
            @RequestParam(value = "serialNo", required = false) String serialNo,
			@RequestParam(value = "projectName", required = false) String projectName,
            @RequestParam(value = "isDealDone", required = false) String isDealDone,
            @RequestParam(value = "isValidate", required = false) String isValidate){

        IPage<Project> result = service.queryProjectByPage(page, pageSize, projectFromDate, projectToDate,
                payFromDate, payToDate, serialNo, projectName, isValidate, isDealDone);

        Map<String,Object> sumMap = service.staticProjectCost(projectFromDate, projectToDate,
                payFromDate, payToDate, serialNo, projectName, isValidate, isDealDone);

        HttpHeaders headers = new HttpHeaders();

        sumMap.forEach((key, value) -> {
            headers.add(key, value.toString());
        });

        ResponseEntity response = new ResponseEntity<>(ResponseResult.ok(result, sumMap), headers, HttpStatus.OK);

        return  response;
    }

    @GetMapping("/projects/export")
    public ResponseEntity exportProject(
            @RequestParam(value = "projectFromDate", required = false) String projectFromDate,
            @RequestParam(value = "projectToDate", required = false) String projectToDate,
            @RequestParam(value = "payFromDate", required = false) String payFromDate,
            @RequestParam(value = "payToDate", required = false) String payToDate,
            @RequestParam(value = "serialNo", required = false) String serialNo,
			@RequestParam(value = "projectName", required = false) String projectName,
            @RequestParam(value = "isDealDone", required = false) String isDealDone,
            @RequestParam(value = "isValidate", required = false) String isValidate){
        List<Project> list = service.queryProject(projectFromDate, projectToDate,
                payFromDate, payToDate, serialNo, projectName, isValidate, isDealDone);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/project/{id}")
    public ResponseResult<ProjectDomain> retrieveProjectById(@PathVariable("id") int id){

        Project project = service.getById(id);

        if (project == null) {
            log.error("Project with id {} not found", id);
            return ResponseResult.notFound("Project with id " + id + " not found");
        }

        ProjectDomain projectDomain = new ProjectDomain();
        BeanUtils.copyProperties(project, projectDomain);

        List<ProjectDetail> details = detailService.listByProjectId(id);

        List<ProjectDetailDomain> detailDomains = new ArrayList<>(details.size());
        for (ProjectDetail detail : details){
            ProjectDetailDomain domain = new ProjectDetailDomain();
            BeanUtils.copyProperties(detail, domain);
            detailDomains.add(domain);
        }

        List<ProjectPay> pays = payService.listByProjectId(id);

        List<ProjectPayDomain> payDomains = new ArrayList<>(pays.size());
        for (ProjectPay pay : pays){
            ProjectPayDomain domain = new ProjectPayDomain();
            BeanUtils.copyProperties(pay, domain);
            payDomains.add(domain);
        }


        projectDomain.setDetails(detailDomains);
        projectDomain.setPays(payDomains);

        return  ResponseResult.ok(projectDomain);
    }

    @PostMapping("/project")
    public ResponseResult<String> createProject(@Validated @RequestBody ProjectDomain projectDomain){
        Project project = new Project();
        BeanUtils.copyProperties(projectDomain, project);

        if (projectDomain.getPays() != null && projectDomain.getPays().size() > 0) {
            BigDecimal total  = new BigDecimal(0);
            for (ProjectPayDomain payDomain : projectDomain.getPays()) {
                total = total.add(payDomain.getPay());
            }
            project.setCostPaid(total);
        }

        service.save(project);

        if (projectDomain.getDetails() != null && projectDomain.getDetails().size() > 0) {
            List<ProjectDetail> details = new ArrayList<>(projectDomain.getDetails().size());
            for (ProjectDetailDomain detailDomain : projectDomain.getDetails()) {
                ProjectDetail detail = new ProjectDetail();
                BeanUtils.copyProperties(detailDomain, detail);
                detail.setProjectId(project.getId());
                details.add(detail);
            }
            detailService.saveBatch(details);
            log.info("{} rows project detail created.", details.size());
        }

        if (projectDomain.getPays() != null && projectDomain.getPays().size() > 0) {
            List<ProjectPay> pays = new ArrayList<>(projectDomain.getDetails().size());
            for (ProjectPayDomain payDomain : projectDomain.getPays()) {
                ProjectPay pay = new ProjectPay();
                BeanUtils.copyProperties(payDomain, pay);
                pay.setProjectId(project.getId());
                pays.add(pay);
            }
            payService.saveBatch(pays);
            log.info("{} rows project pay created.", pays.size());
        }


        return  ResponseResult.ok("添加成功！");
    }

    @PutMapping("/project")
    public ResponseResult<String> updateProject(@Validated @RequestBody ProjectDomain projectDomain){

        Project project = new Project();
        BeanUtils.copyProperties(projectDomain, project);

        if (projectDomain.getPays() != null && projectDomain.getPays().size() > 0) {
            BigDecimal total  = new BigDecimal(0);
            for (ProjectPayDomain payDomain : projectDomain.getPays()) {
                total = total.add(payDomain.getPay());
            }
            project.setCostPaid(total);
        }

        List<ProjectDetail> details = detailService.listByProjectId(project.getId());
        Set<Integer> detailsIdSet = new HashSet<>();
        details.stream().forEach(d -> detailsIdSet.add(d.getId()));

        log.info("process details create, update and delete");
        List<ProjectDetailDomain> projectDetailDomains = projectDomain.getDetails();

        if (projectDetailDomains != null && projectDetailDomains.size() > 0){
            List<ProjectDetail> createList = new ArrayList<>();
            List<ProjectDetail> updateList = new ArrayList<>();
            for(ProjectDetailDomain detailDomain : projectDetailDomains) {
                ProjectDetail detail = new ProjectDetail();
                BeanUtils.copyProperties(detailDomain, detail);
                detail.setProjectId(project.getId());

                if (detailDomain.getId() == 0) {
                    createList.add(detail);
                } else {
                    updateList.add(detail);
                    detailsIdSet.remove(detail.getId());
                }

            }

            if (createList.isEmpty() == false) {
                detailService.saveBatch(createList);
                log.info("{} rows details created.", createList.size());
            }

            if (updateList.isEmpty() == false) {
                detailService.updateBatchById(updateList);
                log.info("{} rows details updated.", updateList.size());
            }

            if (detailsIdSet.isEmpty() == false) {
                detailService.removeByIds(detailsIdSet);
                log.info("{} rows details removed.", detailsIdSet.size());
            }

        }

        log.info("process pays create, update and delete");
        List<ProjectPayDomain> projectPayDomains = projectDomain.getPays();

        List<ProjectDetail> pays = detailService.listByProjectId(project.getId());
        Set<Integer> paysIdSet = new HashSet<>();
        pays.parallelStream().forEach(p -> paysIdSet.add(p.getId()));

        if (projectPayDomains != null && projectPayDomains.size() > 0){
            List<ProjectPay> createList = new ArrayList<>();
            List<ProjectPay> updateList = new ArrayList<>();
            for(ProjectPayDomain payDomain  : projectPayDomains) {
                ProjectPay pay = new ProjectPay();
                BeanUtils.copyProperties(payDomain, pay);
                pay.setProjectId(project.getId());

                if (pay.getId() == 0) {
                    createList.add(pay);
                } else {
                    updateList.add(pay);
                    paysIdSet.remove(pay.getId());
                }

            }

            if (createList.isEmpty() == false) {
                payService.saveBatch(createList);
                log.info("{} rows pays created.", createList.size());
            }

            if (updateList.isEmpty() == false) {
                payService.updateBatchById(updateList);
                log.info("{} rows pays updated.", updateList.size());
            }

            if (paysIdSet.isEmpty() == false) {
                payService.removeByIds(paysIdSet);
                log.info("{} rows pays removed.", paysIdSet.size());
            }

        }

        service.updateById(project);
        return  ResponseResult.ok("修改成功！");
    }

    @DeleteMapping("/project")
    public ResponseResult<Void> deleteProject(@RequestBody List<Integer> ids) {

//        log.info("remove project with ids: {}", String.join(",", ids));

        payService.removeByProjectIds(ids);
        detailService.removeByProjectIds(ids);
        service.removeByIds(ids);

        return ResponseResult.ok();
    }
}
