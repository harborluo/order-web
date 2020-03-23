package com.order.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;



public class CalendarTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1393958372835937986L;
	
	private String property;
	
	private boolean allowClear = true;
	
	private String calendarImgPath = "/CalenderImages/calendar.gif";
	
	private String clearImgPath = "/CalenderImages/clear.gif";
	
	public boolean isAllowClear() {
		return allowClear;
	}



	public void setAllowClear(boolean allowClear) {
		this.allowClear = allowClear;
	}



	public String getProperty() {
		return property;
	}



	public void setProperty(String property) {
		this.property = property;
	}

	public int doStartTag() throws JspException {
		try {
			
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			
			JspWriter out = pageContext.getOut();
			
			StringBuffer sb = new StringBuffer("<img src=\"");
				sb.append(request.getContextPath())
				.append(calendarImgPath)
				.append("\" onclick=\"popUpCalendar(this,document.forms[0].")
				.append(property).append(",'yyyy-mm-dd')\" />");

			if(allowClear){
				sb.append("<img src=\"").append(request.getContextPath())
				  .append(clearImgPath)
				  .append("\" onclick=\"document.forms[0].").append(property)
				  .append(".value='';\" />");
			}
				
			out.print(sb.toString());
			
		} catch (Exception e) {
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}

}
