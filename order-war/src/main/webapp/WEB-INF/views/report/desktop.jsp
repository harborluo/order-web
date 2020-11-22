<%@ page contentType="text/html;charset=GBK"%>
<%@page import="org.jfree.data.category.DefaultCategoryDataset"%>
<%@page import="org.jfree.chart.JFreeChart"%>
<%@page import="org.jfree.chart.ChartFactory"%>
<%@page import="org.jfree.chart.plot.PlotOrientation"%>
<%@page import="org.jfree.chart.servlet.ServletUtilities"%>
<%@page import="java.util.List"%>
<%@ page import="org.jfree.chart.StandardChartTheme" %>
<%@ page import="java.awt.*" %>

<%
	StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
	//���ñ�������
	standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));
	//����ͼ��������
	standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15));
	//�������������
	standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15));
	//Ӧ��������ʽ
	ChartFactory.setChartTheme(standardChartTheme);

DefaultCategoryDataset dataset = new DefaultCategoryDataset();
List list = (List) request.getAttribute("list");
for(int i=0;i<list.size();i++){
	Object[] temp = (Object[]) list.get(i);
	//System.out.println(temp[1].toString()+" ��:"+temp[0].toString());
	dataset.addValue((Double)temp[0], "", temp[1].toString()+"��");
}

String title = "��K���ʯ��"+request.getAttribute("year")+"����տ�ͳ��ͼ��";

//dataset.addValue(1000, "", "2008��");

//JFreeChart chart = ChartFactory.createLineChart   
JFreeChart jfreechart = ChartFactory.createLineChart(title, // ����   
                "�·�", 
                "�տ�", 
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