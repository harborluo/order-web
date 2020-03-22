<%@ page language="java" contentType="text/html; charset=GBK"	pageEncoding="GBK"%>

<%@ taglib prefix="c" uri="c"%>


<html>

	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=GBK">

		<title>在此处插入标题</title>

	</head>

	<body>

		测试: <c:out value="${a1}"/> <c:out value="${a2}"/>

		<br />

		<c:forEach var="list" items="${a3}">

			<c:out value="${list.id}"></c:out>

			<c:if test="${list.id % 2 == 0}">

				<a href="#" style="color: red; border: 1px #ececec solid">
					<c:out value="${list.username}"/> </a>

			</c:if>

			<c:if test="${list.id % 2 != 0}">

				<a href="#" style="color: green; border: 1px #ececec solid">
					<c:out value="${list.username}"></c:out> </a>

			</c:if>

			<c:out value="${list.password}"></c:out>

			<br />

		</c:forEach>

	</body>

</html>

