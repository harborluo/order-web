<%@ page contentType="text/html;charset=GBK"%>
<%@page import="org.jfree.data.category.DefaultCategoryDataset"%>
<%@page import="org.jfree.chart.JFreeChart"%>
<%@page import="org.jfree.chart.ChartFactory"%>
<%@page import="org.jfree.chart.plot.PlotOrientation"%>
<%@page import="org.jfree.chart.servlet.ServletUtilities"%>

<%
DefaultCategoryDataset dataset = new DefaultCategoryDataset();
dataset.addValue(1000, "", "2008年");
dataset.addValue(800, "", "2009年");
dataset.addValue(3000, "", "2010年");
dataset.addValue(500, "", "2011年");
dataset.addValue(1000, "", "2012年");
dataset.addValue(800, "", "2013年");
dataset.addValue(3000, "", "2014年");
dataset.addValue(500, "", "2015年");
dataset.addValue(1000, "", "2016年");
dataset.addValue(800, "", "2017年");
dataset.addValue(3000, "", "2018年");
dataset.addValue(500, "", "2019年");
//JFreeChart chart = ChartFactory.createLineChart   
JFreeChart jfreechart = ChartFactory.createLineChart("粤K万丰石材", // 标题   
                "月份", 
                "收款金额", 
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