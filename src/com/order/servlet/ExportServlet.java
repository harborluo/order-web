package com.order.servlet;



import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.order.dao.GeneralService;
import com.order.orm.Project;
import com.order.orm.ProjectPay;
import com.order.util.WebUtil;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.Number;

public class ExportServlet extends HttpServlet {

	private GeneralService service;
	/**
	 * Constructor of the object.
	 */
	public ExportServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		try {
			
			String hql="from Project p";
			Hashtable params = new Hashtable();
			
			String countHql = "select p.project.id,count(p.project.id) from ProjectPay p";
			
			
			String payHql = "from ProjectPay p";
			
			
			
			String beginDate = req.getParameter("beginDate");
			String endDate = req.getParameter("endDate");
			String isOK = req.getParameter("isOK");
			
			if((WebUtil.isEmpty(beginDate)&&WebUtil.isEmpty(endDate))==false){
				if(WebUtil.isEmpty(beginDate)){
					hql +=" where p.projectDate<=:date";
					countHql +=" where p.project.projectDate<=:date";
					payHql +=" where p.project.projectDate<=:date";
					params.put("date", WebUtil.parseDate(endDate));
				}else if(WebUtil.isEmpty(endDate)){
					hql +=" where p.projectDate>=:date";
					countHql +=" where p.project.projectDate>=:date";
					payHql +=" where p.project.projectDate>=:date";
					params.put("date", WebUtil.parseDate(beginDate));
				}else{
					hql +=" where p.projectDate between :date1 and :date2";
					countHql +=" where p.project.projectDate between :date1 and :date2";
					payHql +=" where p.project.projectDate between :date1 and :date2";
					params.put("date1", WebUtil.parseDate(beginDate));
					params.put("date2", WebUtil.parseDate(endDate));
				}
			}
			
			if(WebUtil.isEmpty(isOK)==false){
				if("yes".equals(isOK)){
					if(params.size()>0){
						hql+=" and p.cost<=p.costPaid";
						countHql+=" and p.project.cost<=p.costPaid";
						payHql+=" and p.project.cost<=p.costPaid";
					}else{
						hql+=" where p.cost<=p.costPaid";
						countHql+=" where p.project.cost<=p.costPaid";
						payHql+=" where p.project.cost<=p.costPaid";
					}
				}else{
					if(params.size()>0){
						hql+=" and (p.cost>p.costPaid or p.costPaid is null)";
					}else{
						hql+=" where (p.cost>p.costPaid or p.costPaid is null)";
					}
				}
			}
			
			String serrialNo = req.getParameter("serrialNo");
			if(WebUtil.isEmpty(serrialNo)==false){
				if(params.size()>0){
					hql+=" and p.serrialNo like :serrialNo";
				}else{
					hql+=" where p.serrialNo like :serrialNo";
				}
				params.put("serrialNo", "%"+serrialNo+"%");
			}
			
			List<Project> list = service.find(hql+" order by p.projectDate desc,p.id desc", params);
			List countList = service.find(countHql+" group by p.project.id",params);
			List<ProjectPay> payList = service.find(payHql,params);
			
			HashMap<Integer,Integer> countCache = new HashMap<Integer, Integer>();
			for(int i=0;i<countList.size();i++){
				Object[] temp = (Object[])countList.get(i);
				countCache.put((Integer)temp[0],((Long)temp[1]).intValue());
			}
			
			
			res.setHeader("Content-Disposition", "attachment; filename=report.xls");
			res.setContentType("application/msexcel");
			
			jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(res.getOutputStream()); 
			jxl.write.WritableSheet sheet = wwb.createSheet("粤K万丰石材", 0); 
			
			int rowIndex = 0;
			
			
			WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, 
				    false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);    
			
			 
			WritableFont detFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, 
					    false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);    
//			WritableCellFormat detFormat = new WritableCellFormat (detFont);    
			
			NumberFormat nf=new NumberFormat("0.00"); //用于Number的格式   
			WritableCellFormat priceFormat = new WritableCellFormat (detFont, nf);    
 
			DateFormat df=new DateFormat("yyyy年M月d日");//用于日期的   
			WritableCellFormat dateFormat = new WritableCellFormat (detFont, df);
			
			WritableCellFormat headerFormat = new WritableCellFormat (headerFont);
			
			for(int i=0;i<13;i++){
				sheet.setColumnView(i, 15);//设置列的宽度为15 
				sheet.mergeCells(i, rowIndex, i, rowIndex+1);
			}
			
			sheet.addCell(new Label(0, rowIndex, "工程名称",headerFormat));
			sheet.addCell(new Label(1, rowIndex, "工程日期",headerFormat));
			sheet.addCell(new Label(2, rowIndex, "单号",headerFormat));
			sheet.addCell(new Label(3, rowIndex, "工程总价",headerFormat));
			sheet.addCell(new Label(4, rowIndex, "已收款",headerFormat));
			sheet.addCell(new Label(5, rowIndex, "加工费",headerFormat));
			sheet.addCell(new Label(6, rowIndex, "材料费",headerFormat));
			sheet.addCell(new Label(7, rowIndex, "开工日期",headerFormat));
			sheet.addCell(new Label(8, rowIndex, "完工日期",headerFormat));
			sheet.addCell(new Label(9, rowIndex, "客户",headerFormat));
			sheet.addCell(new Label(10,rowIndex, "客户电话",headerFormat));
			sheet.addCell(new Label(11,rowIndex, "客户地址",headerFormat));
			sheet.addCell(new Label(12,rowIndex, "备注",headerFormat));
			
			sheet.mergeCells(13, rowIndex, 16, rowIndex);
			sheet.addCell(new Label(13, rowIndex, "收费明细",headerFormat));
			sheet.setColumnView(13, 15);
			sheet.setColumnView(14, 15);
			sheet.setColumnView(15, 15);
			sheet.setColumnView(16, 15);
			sheet.addCell(new Label(13, rowIndex+1, "收款单号",headerFormat));
			sheet.addCell(new Label(14, rowIndex+1, "金额",headerFormat));
			sheet.addCell(new Label(15, rowIndex+1, "日期",headerFormat));
			sheet.addCell(new Label(16, rowIndex+1, "用途",headerFormat));
			
			rowIndex++;
			
			for(Project project:list){
				rowIndex++;
				
				int count = countCache.get(project.getId())==null?0:countCache.get(project.getId());
				if(count>1){
					//2条以上付款记录，合并单元格
					for(int i=0;i<13;i++){
						sheet.mergeCells(i, rowIndex, i, rowIndex+count-1);
					}
					int index=0;
					for(ProjectPay pay:payList){
						if(pay.getProject().getId().intValue()==project.getId().intValue()){
							sheet.addCell(new Label(13, rowIndex+index, pay.getPayNo()));
							sheet.addCell(new Number(14, rowIndex+index,pay.getPay(),priceFormat));
							sheet.addCell(new DateTime(15,rowIndex+index,pay.getPayDate(),dateFormat));
							sheet.addCell(new Label(16, rowIndex+index, pay.getType()));
							//payList.remove(pay);
							index++;
						}
					}
				}else if(count==1){
					//1条付款记录
					for(ProjectPay pay:payList){
						if(pay.getProject().getId().intValue()==project.getId().intValue()){
							sheet.addCell(new Label(13, rowIndex, pay.getPayNo()));
							sheet.addCell(new Number(14, rowIndex,pay.getPay(),priceFormat));
							sheet.addCell(new DateTime(15,rowIndex,pay.getPayDate(),dateFormat));
							sheet.addCell(new Label(16, rowIndex, pay.getType()));
							//payList.remove(pay);
							continue;
						}
					}
				}
				
				sheet.addCell(new Label(0, rowIndex, project.getName()));
				sheet.addCell(new DateTime(1,rowIndex,project.getProjectDate(),dateFormat));
				sheet.addCell(new Label(2, rowIndex, project.getSerrialNo()));
				sheet.addCell(new Number(3, rowIndex, project.getCost(),priceFormat));
				
				if(project.getCostPaid()!=null){
					sheet.addCell(new Number(4, rowIndex, project.getCostPaid(),priceFormat));
				}
				
				sheet.addCell(new Number(5, rowIndex, project.getProcessCost(),priceFormat));
				sheet.addCell(new Number(6, rowIndex, project.getMaterialCost(),priceFormat));
				
				if(project.getBeginDate()!=null){
					sheet.addCell(new DateTime(7,rowIndex,project.getBeginDate(),dateFormat));
				}
				if(project.getFinishDate()!=null){
					sheet.addCell(new DateTime(8,rowIndex,project.getFinishDate(),dateFormat));
				}
				sheet.addCell(new Label(9, rowIndex, project.getClientName()));
				sheet.addCell(new Label(10, rowIndex, project.getClientPhone()));
				sheet.addCell(new Label(11, rowIndex, project.getClientAddress()));
				sheet.addCell(new Label(11, rowIndex, project.getNote()));	
				
				if(count>1){
					rowIndex+=count-1;
				}
				
			}
			
			wwb.write();
			wwb.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		ApplicationContext ctx = WebApplicationContextUtils
		.getRequiredWebApplicationContext(this.getServletContext());
		service = (GeneralService) ctx.getBean("genralDao");
	}

}
