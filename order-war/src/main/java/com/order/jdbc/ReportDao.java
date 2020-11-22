package com.order.jdbc;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;


public interface ReportDao {
	public SqlRowSet query(String sql, Object[] args);
}
