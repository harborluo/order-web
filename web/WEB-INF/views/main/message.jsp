<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="c"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<title>粤K万丰石材-返回消息</title>
	<link href="../../../css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<table width="50%" align="center" border="1" style="margin-top:120px;border-left-color:#3A6EA5;">
		<tr style="background-color:#7db054;height:40px;text-indent:10px;">
			<td>系统消息</td>
		</tr>
		<tr>
			<td style="font-size:20;text-align:center;height:100px">
				<c:out value="${msgText}"></c:out>
			</td>
		</tr>
		<tr bgColor="#cbedaf">
			<td align="center">
				<c:if test="${prePage=='add'}">
					<a href="addProject.do">继续录入</a>
				</c:if>
				<a href="projectList.do">返回列表</a>
			</td>
		</tr>
	</table>
</body>
</html>