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

    @GetMapping("/report")
    public ResponseEntity staticByMetirc(@Pattern(regexp = "^(all|year)$") @RequestParam(name = "scope", defaultValue = "all") String scope
                                         ,@Min(2000) @Max(2199) @RequestParam(name = "value", defaultValue = "2008") int year) {

        List<Map<String,Object>> result = new ArrayList<>();

        if("all".equals(scope)) {
            result = this.projectService.staticProjectCost();
        } else if("year".equals(scope)){
            result = this.projectService.staticProjectCostByYear(year);
        }

        return ResponseEntity.ok(result);
    }

}
