package com.order.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.order.jdbc.ReportDao;

@Controller
public class ReportController {

	private static ReportDao dao = null;
	
	public void setDao(ReportDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 统计余款未收工程报表
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping("/desktop.do") 
	public String search(ModelMap map,HttpServletRequest request,HttpServletResponse response) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		String sql="select sum(cost_paid),month(project_date) from project" +
				" where year(project_date)=? group by month(project_date) ";
		//Hashtable params = new Hashtable();
		//params.put("year", year);
		
		SqlRowSet set = dao.query(sql, new Object[]{year});
		List list = new ArrayList(12);
		while(set.next()){
			Object[] temp = new Object[2];
			temp[0] = set.getDouble(1);
			temp[1] = set.getInt(2);
			list.add(temp);
		}
		request.setAttribute("list",list);
		request.setAttribute("year",year);
		
		return "report/desktop";
	}
}
