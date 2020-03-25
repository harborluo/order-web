package com.harbor.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DruidDataSourceConfigTest {

    @Autowired
    @Qualifier(value = "dataSource")
    DataSource dataSource;

//    @Autowired
//    @Qualifier(value = "slaveDataSource")
//    DataSource slaveDataSource;

    @Test
    public void testMasterDatasource() throws SQLException {

        log.info("数据源类名 {}", dataSource.getClass().getCanonicalName());
        Connection connection = dataSource.getConnection();

        log.info("数据源 连接>>>>>>>>>" + connection);
        log.info("数据源 url is: {}", connection.getMetaData().getURL());
        connection.close();

    }


}
