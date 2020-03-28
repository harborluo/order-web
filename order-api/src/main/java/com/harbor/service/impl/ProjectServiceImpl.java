package com.harbor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harbor.entity.Project;
import com.harbor.mapper.ProjectMapper;
import com.harbor.service.ProjectService;
import org.springframework.stereotype.Service;

/**
 * Created by harbor on 2020/3/28.
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

}
