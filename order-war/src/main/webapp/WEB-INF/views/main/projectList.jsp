<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%--<%@ taglib prefix="c" uri="c"%>--%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="h" uri="spring-form"%>
<%@ taglib prefix="harbor" uri="harbor"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<script src="js/calendar.js" type="text/javascript"></script>
	<script src="js/common.js" type="text/javascript"></script>
	<title>粤K万丰石材</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
  <h:form>
  	<input type="hidden" name="page" >
  	<table width="100%">
			<tr>
				<td style="border: 1px dashed #999999">  
				<c:choose>
					<c:when test="${dateType=='projectDate'}">
						工程总价统计：<b><c:out value='${costTotal}'/></b>&nbsp;&nbsp;&nbsp;
						收款统计：<b><font color="blue"><c:out value='${costPaidTotal}'/></font></b>&nbsp;&nbsp;
						余款未收：<b><font color="red"><c:out value='${costTotal-costPaidTotal}'/></font></b>
					</c:when>
					<c:otherwise>
						收款统计：<b><font color="blue"><c:out value='${costPaidTotal}'/></font></b>&nbsp;&nbsp;
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</table>		
  	<table width="100%">
			<tr>
				<td style="border: 1px dashed #999999" colspan="2">  	
  				<select name="dateType">
    				<option value="projectDate">工程日期</option>
    				<option value="payDate">支付日期</option>
    			</select>从
    			<input type="text" name="beginDate" size="10" value="<c:out value='${beginDate}'/>"/>
    			<harbor:calendar property="beginDate"/>
    			到
    			<input type="text" name="endDate" size="10" value="<c:out value='${endDate}'/>"/>
    			<harbor:calendar property="endDate"/>
    			收款情况：
    			<select name="isOK">
    				<option value="">=请选择=</option>
    				<option value="yes">已收齐</option>
    				<option value="no">未收齐</option>
    			</select>
    			单号:	<input type="text" name="serrialNo" size="20" value="<c:out value='${serrialNo}'/>"/>
    	</tr>
    	<tr>
    		<td style="border: 1px dashed #99999">
    			<c:choose>
    			<c:when test="${isValidate=='N'}">
    				<input type="checkbox" name="isValidate" value="N" checked>
    			</c:when>
    			<c:otherwise>
    				<input type="checkbox" name="isValidate" value="N">
    			</c:otherwise>
    			</c:choose>    			
    			录入不完整的数据	
    		</td>
    		<td align="right" style="border: 1px dashed #999999">
    			<input type="button" style="width:80px" name="btnSearch" value="查找" onClick="doSearch()">
    			<input type="button" style="width:80px" name="btnAdd" value="录入" onClick="doAdd()">
    			<input type="button" style="width:80px" name="btnDel" value="删除" onClick="doDel()">
    			<input type="button" style="width:80px" name="btnExport" value="导出Excel" onClick="doExport()">
    		</td>
    	</tr>
    </table>	
    <table border="1" cellpadding="0" cellspacing="0" style="border: 1px dashed #999999" bgcolor="#FFFFFF" id="dataTable">
    <tr align="center" bgColor="#efeea">
    	<td><input type="checkbox" name="all" onClick="selectAll(this.checked)"/></td>
    	<td width="150px">工程名称</td>
    	<td width="80px">单号</td>
    	<td width="90px">工程日期</td>
    	<td width="70px">工程总价</td>
    	<td width="70px">收款情况</td>
    	<td width="100px">客户</td>
    	<td width="100px">电话</td>
    	<td>地址</td>
    </tr>
    <c:forEach var="project" items="${projectList}">
    	<tr>
    		<td align="center"><input type="checkbox" name="id" value="<c:out value='${project.id}'/>"/></td>
    		<td>
    			<a href ="loadProject.do?id=<c:out value='${project.id}'/>">
    				<c:out value="${project.name}"/>
    			</a>
    		</td>
    		<td>
	   			<c:choose>
	   				<c:when test="${empty project.serrialNo or project.serrialNo==''}">
	   					未开单
	   				</c:when>
    				<c:otherwise>
    					<c:out value="${project.serrialNo}"/>
    				</c:otherwise>    		
    			</c:choose>
    		</td>
    		<td><c:out value="${project.projectDate}"/></td>
    		<td align="right">
    			<c:choose>
    				<c:when test="${empty project.cost}">
    					未入工程总价    					
    				</c:when>
    				<c:otherwise>
							<c:out value="${project.cost}"/>
						</c:otherwise>
					</c:choose>
				</td>
    		<td align="right">
    			<c:choose>
    				<c:when test="${empty project.costPaid}">
    					未付款    					
    				</c:when>
    				<c:when test="${project.cost == project.costPaid}">
    					<font color="blue">OK</font>
    				</c:when>
    				<c:otherwise>
							<c:out value="${project.costPaid}"/>
						</c:otherwise>
					</c:choose>
    		</td>
    		<td><c:out value="${project.clientName}"/>&nbsp;</td>
    		<td><c:out value="${project.clientPhone}"/>&nbsp;</td>
    		<td noWrap><c:out value="${project.clientAddress}"/>&nbsp;</td>
    	</tr>
    </c:forEach>    
    </table>
    <table width="100%">
    	<tr>
    		<td class="page"><harbor:link pageIndex="pageIndex" recordCount="recordCount" pageSize="pageSize"/></td>
    	</tr>
    </table>
    <iframe name="exportFrame" style="display:none" src=""></iframe>
  </h:form>
</body>
<script type="text/javascript">
function doAdd(){
	window.location="<%=request.getContextPath()%>/addProject.do";
}

function gotoPage(n){
	document.forms[0].page.value=n;
	document.forms[0].submit();
}

function doSearch(){
	document.forms[0].submit();
}

function doDel(){
	if(confirm("确实要删除数据吗？")==false){
		return;
	}
	document.forms[0].action="delProject.do";
	document.forms[0].submit();
}

window.onLoad = formatTable('dataTable');

function selectAll(check){
	var boxs = document.all("id");
	for(var i=0;i<boxs.length;i++){
		boxs[i].checked = check;
	}
}

function doExport(){
  var url="/order/export.xls";
  var f = document.forms[0];
  url+="?beginDate="+f.beginDate.value;
  url+="&endDate="+f.endDate.value;
  url+="&isOK="+f.isOK.value;
	document.all("exportFrame").src=url;
}

document.all("isOK").value="<c:out value='${isOK}'/>";
document.all("dateType").value="<c:out value='${dateType}'/>";
</script>
</html>