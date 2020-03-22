package com.order.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;


public class LinkBarTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7198507766030931612L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LinkBarTag.class);

	private String pageIndex = "pageIndex";
	
	private String recordCount = "recordCount";
	
	private String pageSize = "pageSize";

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public int doStartTag() throws JspException {
//		HttpSession session = pageContext.getSession();
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		
		int currPage = Integer.parseInt(request.getAttribute(pageIndex).toString());
		int size = Integer.parseInt(request.getAttribute(pageSize).toString());
		int count =  Integer.parseInt(request.getAttribute(recordCount).toString());
		
		int totalPage = count/size;
		
		if(count==0||count%size>0){
			totalPage +=1; 
		}
		
		StringBuffer linkText = new StringBuffer();
		
		linkText.append("第<font color='red'><b>").append(currPage).append("</b></font>");
		linkText.append("/<font color='red'><b>").append(totalPage).append("</b></font>页&nbsp;");
		linkText.append("每页<font color='red'><b>").append(size).append("</b></font>条&nbsp;");
		linkText.append("共<font color='red'><b>").append(count).append("</b></font>条&nbsp;");
//		linkText.append("分页：&nbsp;");
		
		if(currPage!=1){
			linkText.append("<a href='javascript:gotoPage(1)'><b>首页</b></a>&nbsp;");
		}else{
			linkText.append("<font color='gray'><b>首页</b></font>&nbsp;");
		}
		
		if(currPage>1){
			linkText.append("<a href='javascript:gotoPage(").append(currPage-1).append(")'><b>上一页</b></a>&nbsp;");
		}else{
			linkText.append("<font color='gray'><b>上一页</b></font>&nbsp;");
		}
		
		if(currPage<totalPage){
			linkText.append("<a href='javascript:gotoPage(").append(currPage+1).append(")'><b>下一页</b></a>&nbsp;");
		}else{
			linkText.append("<font color='gray'><b>下一页</b></font>&nbsp;");
		}
		
		if(currPage!=totalPage&&totalPage!=0){
			linkText.append("<a href='javascript:gotoPage(").append(totalPage).append(")'><b>尾页</b></a>&nbsp;");
		}else{
			linkText.append("<font color='gray'><b>尾页</b></font>&nbsp;");
		}
		
		linkText.append("转到第&nbsp;<select onchange='javascript:gotoPage(this.value)'>");
		
		for(int i=1;i<=totalPage;i++){
			linkText.append("<option value='").append(i).append("'")
			.append(i==currPage?" selected>":">")
			.append(i).append("</option>");
		}
		
		linkText.append("</select>&nbsp;页");
		
		try {
			pageContext.getOut().write(linkText.toString());
		} catch (IOException e) {
			logger.warn("输出分布连接出错:"+e.getMessage());

		}

		return EVAL_PAGE;
	}
	
}
