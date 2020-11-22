<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="GBK"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title></title>
		<link rel="stylesheet" type="text/css" href="js/tree.css">
		<link rel="StyleSheet" href="dtree/tree.css" type="text/css" />
		<script type="text/javascript" src="dtree/tree.js"></script>
		
		<meta http-equiv="Pragma" content="No-cache">   
  	<meta http-equiv="Cache-Control" content="no-cache,must-revalidate">   
  	<meta http-equiv="Expires" content="-1">
  	
	<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	background-color: #7bbee4;
}
-->
</style></head>
	
<body>
<table style="top-margin:0px;left-margin:0px;" width="94%" border="0" align="left" cellpadding="0" cellspacing="0">
  <tr>
    <td id="treeContainer">
		</td>
  </tr>
</table>
</body>
<SCRIPT type=text/javascript>
var d=new Date();
var tree=new dTree(0,false,'/order');
tree.add(1,0,"主菜单","","rightFrame");
tree.add(2,1,"工程管理","","rightFrame");
tree.add(203,2,"资料查询","projectList.do","rightFrame");
tree.add(202,2,"数据录入","addProject.do","rightFrame");
tree.add(201,2,"报表统计","desktop.do","rightFrame");
tree.write(document.getElementById("treeContainer"));
tree.openAll();
</SCRIPT>
</html>
