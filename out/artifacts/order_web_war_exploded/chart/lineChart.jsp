<%@ page contentType="text/html;charset=GBK"%>
<%@page import="org.jfree.data.category.DefaultCategoryDataset"%>
<%@page import="org.jfree.chart.JFreeChart"%>
<%@page import="org.jfree.chart.ChartFactory"%>
<%@page import="org.jfree.chart.plot.PlotOrientation"%>
<%@page import="org.jfree.chart.servlet.ServletUtilities"%>

<%
DefaultCategoryDataset dataset = new DefaultCategoryDataset();
dataset.addValue(1000, "", "2008��");
dataset.addValue(800, "", "2009��");
dataset.addValue(3000, "", "2010��");
dataset.addValue(500, "", "2011��");
dataset.addValue(1000, "", "2012��");
dataset.addValue(800, "", "2013��");
dataset.addValue(3000, "", "2014��");
dataset.addValue(500, "", "2015��");
dataset.addValue(1000, "", "2016��");
dataset.addValue(800, "", "2017��");
dataset.addValue(3000, "", "2018��");
dataset.addValue(500, "", "2019��");
//JFreeChart chart = ChartFactory.createLineChart   
JFreeChart jfreechart = ChartFactory.createLineChart("��K���ʯ��", // ����   
                "�·�", 
                "�տ���", 
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
	<title>��K���ʯ��</title>
	</head>
<body>
<center>
	<img src="<%= graphURL %>" width=780 height=500 border=0 usemap="#<%= filename %>">
</center> 
</body>
</html>