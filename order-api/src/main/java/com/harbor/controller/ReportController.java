package com.harbor.controller;

import com.harbor.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@Slf4j
public class ReportController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/report/all")
    public ResponseEntity staticByMetirc() {

        List<Map<String,Object>> result = this.projectService.staticProjectCost();

        log.info("count of metric by year is {}", result.size());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/report/year")
    public ResponseEntity staticByYear(@Min(2000) @Max(2199) @RequestParam(name = "year", defaultValue = "2008") int year) {

        List<Map<String,Object>> result = result = this.projectService.staticProjectCostByYear(year);

        log.info("count of metric for year {} by month is {}", year, result.size());

        return ResponseEntity.ok(result);
    }
}
