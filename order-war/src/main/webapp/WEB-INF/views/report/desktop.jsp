<%@ page contentType="text/html;charset=GBK"%>
<%@page import="org.jfree.data.category.DefaultCategoryDataset"%>
<%@page import="org.jfree.chart.JFreeChart"%>
<%@page import="org.jfree.chart.ChartFactory"%>
<%@page import="org.jfree.chart.plot.PlotOrientation"%>
<%@page import="org.jfree.chart.servlet.ServletUtilities"%>
<%@page import="java.util.List"%>

<%
DefaultCategoryDataset dataset = new DefaultCategoryDataset();
List list = (List) request.getAttribute("list");
for(int i=0;i<list.size();i++){
	Object[] temp = (Object[]) list.get(i);
	//System.out.println(temp[1].toString()+" 月:"+temp[0].toString());
	dataset.addValue((Double)temp[0], "", temp[1].toString()+"月");
}

String title = "粤K万丰石材"+request.getAttribute("year")+"年度收款统计图表";

//dataset.addValue(1000, "", "2008年");

//JFreeChart chart = ChartFactory.createLineChart   
JFreeChart jfreechart = ChartFactory.createLineChart(title, // 标题   
                "月份", 
                "收款", 
                dataset, // dataset   
                PlotOrientation.VERTICAL, false, // legend   
                true, // tooltips   
                false); // URLs
String filename = ServletUtilities.saveChartAsPNG(jfreechart, 780, 500, null, session);    
String graphURL =request.getContextPath()+"/servlet/DisplayChart?filename="+filename;             
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<title>粤K万丰石材</title>
	</head>
<body>
<center>
	<img src="<%= graphURL %>" width=780 height=500 border=0 usemap="#<%= filename %>">
</center> 
</body>
</html>