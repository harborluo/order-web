package com.order.jdbc;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

public class ReportDaoImpl extends JdbcDaoSupport implements ReportDao {
	
	public SqlRowSet query(String sql,Object[] args){
		return getJdbcTemplate().queryForRowSet(sql, args);
	}
}	
