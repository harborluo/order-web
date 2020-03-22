package com.order.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.order.orm.Project;
import com.order.orm.ProjectDetail;
import com.order.orm.ProjectPay;
import com.order.util.WebUtil;
import com.order.dao.GeneralService;

@Controller
public class ProjectController {
	
	private static GeneralService dao = null;

	public void setDao(GeneralService dao) {
		this.dao = dao;
	}
	/**
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping("/projectList.do") 
    public String search(ModelMap map,HttpServletRequest request,HttpServletResponse response) {
		
		String hql = "from Project p";
		String payHql = "select sum(a.pay) from ProjectPay a ";
		
		Hashtable params = new Hashtable();
		int page = WebUtil.getPage(request);	
		Integer recordCount = 0 ;

		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String dateType = request.getParameter("dateType");
		
		boolean hasCond = false;
		
		if(WebUtil.isEmpty(dateType)){
			dateType = "projectDate";
		}
		
		if((WebUtil.isEmpty(beginDate)&&WebUtil.isEmpty(endDate))==false){
			hasCond = true;
			if("projectDate".equals(dateType)){
			//按工程日期查询
				if(WebUtil.isEmpty(beginDate)==false&&WebUtil.isEmpty(endDate)){
					hql +=" where p.projectDate>=:date";
					params.put("date", WebUtil.parseDate(beginDate));
				}else if(WebUtil.isEmpty(endDate)==false&&WebUtil.isEmpty(beginDate)){
					hql +=" where p.projectDate<=:date";
					params.put("date", WebUtil.parseDate(endDate));
				}else{
					hql +=" where p.projectDate between :date1 and :date2";
					params.put("date1", WebUtil.parseDate(beginDate));
					params.put("date2", WebUtil.parseDate(endDate));
				}
			}else{
			//按收款日期查询	
				if(WebUtil.isEmpty(beginDate)==false&&WebUtil.isEmpty(endDate)){
					hql +="  where p.id in(select a.project.id from ProjectPay a where a.payDate>=:date)";
					payHql+=" where a.payDate>=:date";
					params.put("date", WebUtil.parseDate(beginDate));
				}else if(WebUtil.isEmpty(endDate)==false&&WebUtil.isEmpty(beginDate)){
					hql +="  where p.id in(select a.project.id from ProjectPay a where a.payDate<=:date)";
					payHql +=" where a.payDate<=:date";
					params.put("date", WebUtil.parseDate(endDate));

				}else{
					hql +=" where p.id in(select a.project.id from ProjectPay a where a.payDate between :date1 and :date2)";
					payHql +="where a.payDate between :date1 and :date2";
					params.put("date1", WebUtil.parseDate(beginDate));
					params.put("date2", WebUtil.parseDate(endDate));
				}
			}
			
		}
		
		String isOK = request.getParameter("isOK");
		if(WebUtil.isEmpty(isOK)==false){
			if("yes".equals(isOK)){
				if(hasCond){
					hql+=" and p.cost<=p.costPaid";
					payHql += "and a.project.cost<=a.project.costPaid";
				}else{
					payHql+=" where a.project.cost<=a.project.costPaid";
					hql+=" where p.cost<=p.costPaid";
				}
			}else{
				if(hasCond){
					hql+=" and (p.cost>p.costPaid or p.costPaid is null or p.cost=0)";
					payHql += "and (a.project.cost>a.project.costPaid or a.project.costPaid is null or a.project.cost=0)";
				}else{
					hql+=" where (p.cost>p.costPaid or p.costPaid is null or p.cost=0)";
					payHql += " where (a.project.cost>a.project.costPaid or a.project.costPaid is null or a.project.cost=0)";
				}
			}
			hasCond = true;
		}
		
		String serrialNo = request.getParameter("serrialNo");
		if(WebUtil.isEmpty(serrialNo)==false){
			if(hasCond){
				hql+=" and p.serrialNo like :serrialNo";
				payHql+=" and a.project.serrialNo like :serrialNo";
				
			}else{
				hql+=" where p.serrialNo like :serrialNo";
				payHql+=" where a.project.serrialNo like :serrialNo";
			}
			params.put("serrialNo", "%"+serrialNo+"%");
			hasCond = true;
		}
		
		String isValidate = request.getParameter("isValidate");
		if("N".equals(isValidate)){
			if(hasCond){
				hql+=" and p.id in(select a.project.id from ProjectPay a where a.pay is null or a.payDate is null) ";
			}else{
				hql+=" where p.id in(select a.project.id from ProjectPay a where a.pay is null or a.payDate is null) ";
			}
			hasCond = true;
		}
		
		List list = dao.queryPageList(hql+" order by p.projectDate desc,p.id desc", params, page, WebUtil.PAGE_SIZE);
		map.put("projectList", list);
		recordCount = dao.getRecordCount("select count(p.id) "+hql, params);
		
		if("projectDate".equals(dateType)){
			List staticList = dao.find("select sum(p.cost),sum(p.costPaid) "+hql, params);
			Object[] temp = (Object[]) staticList.get(0);
			map.put("costTotal", temp[0]);
			map.put("costPaidTotal", temp[1]);
		}else{
			List staticList = dao.find(payHql, params);
			Object temp = (Object) staticList.get(0);
			map.put("costPaidTotal", temp);
		}
		
		request.setAttribute("recordCount", recordCount);
		request.setAttribute("pageIndex", page+"");
		request.setAttribute("pageSize", WebUtil.PAGE_SIZE+"");
		map.put("dateType", dateType);
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("serrialNo", serrialNo);
		map.put("isOK", isOK);
		map.put("isValidate",isValidate);
		return "main/projectList";
	}
	/**
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping("/addProject.do")     
	public String addProject(ModelMap map,HttpServletRequest request,HttpServletResponse response) {
		ProjectForm vo = new ProjectForm();
		vo.setProjectDate(new Date());
		vo.setProcessCost(new Double(0));
		vo.setMaterialCost(new Double(0));
		map.addAttribute("project", vo);
		
		return "main/addProject";
	}
	/**
	 * 
	 * @param vo
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping("/saveProject.do") 
	public String saveProject(@ModelAttribute("project") ProjectForm vo,ModelMap map,
			HttpServletRequest request,HttpServletResponse response) {
		Project po = new Project();
		po.setBeginDate(vo.getBeginDate());
		po.setClientAddress(vo.getClientAddress());
		po.setClientName(vo.getClientName());
		po.setClientPhone(vo.getClientPhone());
		po.setCost(vo.getCost());
		po.setCostPaid(vo.getCostPaid());
		po.setFinishDate(vo.getFinishDate());
		po.setName(vo.getName());
		po.setNote(vo.getNote());
		po.setSerrialNo(vo.getSerrialNo());
		po.setProjectDate(vo.getProjectDate());
		po.setProcessCost(vo.getProcessCost());
		po.setMaterialCost(vo.getMaterialCost());
		dao.save(po);
		
		//项目明细		
		String[] material = request.getParameterValues("detailCol1");
		if(material!=null){
			String[] length = request.getParameterValues("detailCol2");
			String[] width = request.getParameterValues("detailCol3");
			String[] quantity = request.getParameterValues("detailCol4");
			String[] price = request.getParameterValues("detailCol5");
			for(int i=0,size = material.length;i<size;i++){
				ProjectDetail detail = new ProjectDetail();
				detail.setMaterial(material[i]);
				detail.setLength(WebUtil.parseDouble(length[i]));
				detail.setWidth(WebUtil.parseDouble(width[i]));
				detail.setQuantity(WebUtil.parseInteger(quantity[i]));
				detail.setPrice(WebUtil.parseDouble(price[i]));
				detail.setProject(po);
				dao.save(detail);
			}			
		}
		//收款明细
		String[] pay = request.getParameterValues("payCol1");
		if(pay!=null){
			String[] payDate = request.getParameterValues("payCol2");
			String[] type = request.getParameterValues("payCol3");
			String[] payee = request.getParameterValues("payCol4");
			String[] payno = request.getParameterValues("payCol5");
			for(int i=0,size = pay.length;i<size;i++){
				ProjectPay payment = new ProjectPay();
				payment.setPay(WebUtil.parseDouble(pay[i]));
				payment.setPayDate(WebUtil.parseDate(payDate[i]));
				payment.setType(type[i]);
				payment.setPayee(payee[i]);
				payment.setPayNo(payno[i]);
				payment.setProject(po);
				dao.save(payment);
			}
		}
		
		String hql="update Project set costPaid=" +
				"(select sum(p.pay) from ProjectPay p where p.project.id=:pid),cost=processCost+materialCost where id=:pid";
		Hashtable params = new Hashtable();
		params.put("pid", po.getId());
		dao.execute(hql, params);
		
		
		map.put("msgText", "数据录入成功！");
		map.put("prePage", "add");
		return "main/message";
	}
	/**
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping("/delProject.do")     
	public String delProject(ModelMap map,HttpServletRequest request,HttpServletResponse response) {
		String[] ids = request.getParameterValues("id");
		if(ids!=null){
			String hql = "delete from Project where id in (:id)";
			Hashtable params = new Hashtable();
			params.put("id", WebUtil.toIntegerArray(ids));
			dao.execute(hql, params);
			map.put("msgText", "数据删除成功！");
		}else{
			map.put("msgText", "请选择要删除的数据！");
		}
		map.put("prePage", "del");
		return "main/message";
	}
	/**
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping("/loadProject.do") 
	public String loadProject(ModelMap map,HttpServletRequest request,HttpServletResponse response) {
		
		String id = request.getParameter("id");
		
		Project po = (Project) dao.get(Project.class, new Integer(id));
		
		ProjectForm vo = new ProjectForm();
		
		vo.setId(po.getId());
		vo.setBeginDate(po.getBeginDate());
		vo.setClientAddress(po.getClientAddress());
		vo.setClientName(po.getClientName());
		vo.setClientPhone(po.getClientPhone());
		vo.setCost(po.getCost());
		vo.setCostPaid(po.getCostPaid());
		vo.setFinishDate(po.getFinishDate());
		vo.setName(po.getName());
		vo.setNote(po.getNote());
		vo.setSerrialNo(po.getSerrialNo());
		vo.setProjectDate(po.getProjectDate());
		vo.setProcessCost(po.getProcessCost());
		vo.setMaterialCost(po.getMaterialCost());
		map.addAttribute("project", vo);
		
		String hql = "from ProjectDetail d where d.project.id=:pid";
		Hashtable params = new Hashtable();
		params.put("pid", new Integer(id));
		List detailList = dao.find(hql, params);
		
//		for(int i=0;i<detailList.size();i++){
//			System.out.println(((ProjectDetail)detailList.get(i)).getMaterial());
//		}
		
		
		request.setAttribute("detailList", detailList);
		
		hql = "from ProjectPay p where p.project.id = :pid";
		List payList = dao.find(hql, params);
		request.setAttribute("payList", payList);
		
		return "main/editProject";
	}
	
	/**
	 * 
	 * @param vo
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping("/updateProject.do") 
	public String updateProject(@ModelAttribute("project") ProjectForm vo,ModelMap map,
			HttpServletRequest request,HttpServletResponse response) {
		
		Project po = new Project();
		po.setId(vo.getId());
		po.setBeginDate(vo.getBeginDate());
		po.setClientAddress(vo.getClientAddress());
		po.setClientName(vo.getClientName());
		po.setClientPhone(vo.getClientPhone());
		po.setCost(vo.getCost());
		po.setCostPaid(vo.getCostPaid());
		po.setFinishDate(vo.getFinishDate());
		po.setName(vo.getName());
		po.setNote(vo.getNote());
		po.setSerrialNo(vo.getSerrialNo());
		po.setProjectDate(vo.getProjectDate());
		po.setProcessCost(vo.getProcessCost());
		po.setMaterialCost(vo.getMaterialCost());
		dao.update(po);
		
		String hql = "";
		Hashtable params = new Hashtable();
		params.put("pid", new Integer(po.getId()));
		
		//项目明细		
		String[] did = request.getParameterValues("did");
		if(did!=null){
			String[] material = request.getParameterValues("detailCol1");
			String[] length = request.getParameterValues("detailCol2");
			String[] width = request.getParameterValues("detailCol3");
			String[] quantity = request.getParameterValues("detailCol4");
			String[] price = request.getParameterValues("detailCol5");
			
			hql = "from ProjectDetail d where d.project.id=:pid";;
			List list = dao.find(hql, params);
			
			HashMap<Integer,ProjectDetail> cache = new HashMap<Integer,ProjectDetail>();
			for(int i=0,size=list.size();i<size;i++){
				ProjectDetail temp = (ProjectDetail) list.get(i);
				cache.put(temp.getId(), temp);
			}
			
			for(int i=0,size=did.length;i<size;i++){
				if(WebUtil.isEmpty(did[i])){
					ProjectDetail detail = new ProjectDetail();
					detail.setMaterial(material[i]);
					detail.setLength(WebUtil.parseDouble(length[i]));
					detail.setWidth(WebUtil.parseDouble(width[i]));
					detail.setQuantity(WebUtil.parseInteger(quantity[i]));
					detail.setPrice(WebUtil.parseDouble(price[i]));
					detail.setProject(po);
					dao.save(detail);
				}else{
					ProjectDetail detail = cache.get(new Integer(did[i]));
					detail.setMaterial(material[i]);
					detail.setLength(WebUtil.parseDouble(length[i]));
					detail.setWidth(WebUtil.parseDouble(width[i]));
					detail.setQuantity(WebUtil.parseInteger(quantity[i]));
					detail.setPrice(WebUtil.parseDouble(price[i]));
					detail.setProject(po);
					dao.update(detail);
					cache.remove(new Integer(did[i]));
				}
			}
			
			Iterator<Integer> it = cache.keySet().iterator(); 
			while(it.hasNext()){
				dao.delete(cache.get(it.next()));
			}
			
		}else{
			hql = "delete from ProjectDetail d where d.project.id=:pid";
			dao.execute(hql, params);
		}
		
		
		
		//收款明细
		String[] pid = request.getParameterValues("pid");
		if(pid!=null){
			String[] pay = request.getParameterValues("payCol1");
			String[] payDate = request.getParameterValues("payCol2");
			String[] type = request.getParameterValues("payCol3");
			String[] payee = request.getParameterValues("payCol4");
			String[] payno = request.getParameterValues("payCol5");
			hql = "from ProjectPay p where p.project.id = :pid";;
			List list = dao.find(hql, params);
			HashMap<Integer,ProjectPay> cache = new HashMap<Integer,ProjectPay>();
			for(int i=0,size=list.size();i<size;i++){
				ProjectPay temp = (ProjectPay) list.get(i);
				cache.put(temp.getId(), temp);
			}
			for(int i=0,size=pid.length;i<size;i++){
				if(WebUtil.isEmpty(pid[i])){
					ProjectPay payment = new ProjectPay();
					payment.setPay(WebUtil.parseDouble(pay[i]));
					payment.setPayDate(WebUtil.parseDate(payDate[i]));
					payment.setType(type[i]);
					payment.setPayee(payee[i]);
					payment.setPayNo(payno[i]);
					payment.setProject(po);
					dao.save(payment);
				}else{
					ProjectPay payment = cache.get(new Integer(pid[i]));
					payment.setPay(WebUtil.parseDouble(pay[i]));
					payment.setPayDate(WebUtil.parseDate(payDate[i]));
					payment.setType(type[i]);
					payment.setPayee(payee[i]);
					payment.setPayNo(payno[i]);
					payment.setProject(po);
					dao.update(payment);
					cache.remove(new Integer(pid[i]));
				}
			}
			
			Iterator<Integer> it = cache.keySet().iterator(); 
			while(it.hasNext()){
				dao.delete(cache.get(it.next()));
			}
			
		}else{
			hql = "delete from ProjectPay d where d.project.id=:pid";
			dao.execute(hql, params);
		}
		
		hql="update Project set costPaid=" +
			"(select sum(p.pay) from ProjectPay p where p.project.id=:pid),cost=processCost+materialCost where id=:pid";
		dao.execute(hql, params);
		
		map.put("msgText", "数据修改成功！");
		map.put("prePage", "edit");
		
		return "main/message";
	}
}
