<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="h" uri="spring-form"%>
<%@ taglib prefix="harbor" uri="harbor"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<script src="js/calendar.js" type="text/javascript"></script>
	<script src="js/common.js" type="text/javascript"></script>
	<title>粤K万丰石材-修改资料</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<style type="text/css">
	.bar{
			background-color:#6699FF;
			width:100%;
			color:#FFFFFF;
			font-size:18px;
			padding:5px;
			font-weight:900;
			text-align:left;
	}
	.label{
			margin-top:1px;
			width:25%;
			background-color:#eeeeee;
			text-align:right;
			padding-top:6px;
			padding-right:2px;
			font-size:14px;
			height:25px;
			float:left;
	}
	
	.buttonBar{
			margin:0px;
			margin-top:1px;
			width:100%;
			float:left;
			height:30px;
			padding:5px;
			text-align:center;
			font-size:16px;
			font-family:"宋体";
			background-color:#d7e6d4;
			font-weight:900;
			margin:0px;
			margin-top:1px;
	}
	.inputTable{
		border:#6699FF solid 1px;
	}
	.inputTable td{
		border:#eeeeee solid 1px;
		margin:0px;
	}
	</style>
</head>
<body>
	<h:form commandName="project" modelAttribute="project" action="updateProject.do">
		<h:hidden path="id"/>
		<table width="780" align="center" cellspacing="1" class="inputTable">
			<tr>
				<td colspan="4" class="bar">修改资料</td>
			</tr>	
			<tr>
				<td width="15%" class="label">工程名称</td>
				<td width="35%"><h:input path="name" cssStyle="width:100%"/></td>
				<td width="15%" class="label">工程日期</td>
				<td width="35%">
					<h:input path="projectDate" size="10"/>
					<harbor:calendar property="projectDate"/>
				</td>
			</tr>
			<tr>
				<td class="label">工程总价</td>
				<td><h:input path="cost" cssStyle="text-align:right" readonly="true"/></td>
				<td class="label">已收款</td>
				<td><h:input path="costPaid" cssStyle="text-align:right" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label">加工费</td>
				<td><h:input path="processCost" cssStyle="text-align:right"/></td>
				<td class="label">材料费</td>
				<td><h:input path="materialCost" cssStyle="text-align:right"/></td>
			</tr>			
			<tr>
				<td class="label">开工日期</td>
				<td><h:input path="beginDate" size="10"/><harbor:calendar property="beginDate"/></td>
				<td class="label">完工日期</td>
				<td><h:input path="finishDate" size="10"/><harbor:calendar property="finishDate"/></td>
			</tr>
			<tr>
				<td class="label">单号</td>
				<td><h:input path="serrialNo"/></td>
				<td class="label">客户</td>
				<td><h:input path="clientName"/></td>
			</tr>
			<tr>
				<td class="label">客户电话</td>
				<td><h:input path="clientPhone" cssStyle="width:100%"/></td>
				<td class="label">客户地址</td>
				<td><h:input path="clientAddress" cssStyle="width:100%"/></td>
			</tr>
			<tr>
				<td class="label">备注</td>
				<td colspan="3"><h:textarea path="note" cols="70" cssClass="input" rows="3"/></td>
			</tr>
			<!-- --------- -->
			<tr>
				<td colspan="4">
					<fieldset>
						<legend>项目明细</legend>
						<table width="100%" id="detailTable">
							<tr bgColor="#81bd77">
								<td width="25%">材料</td>
								<td width="15%">长度</td>
								<td width="15%">宽度</td>
								<td width="15%">数量</td>
								<td width="15%">单价</td>
								<td width="10%" align="center">
									<input type="button" name="btnAdd" value=" + " onClick="addDetail()">
								</td>
								<td width="0" style="display:none"></td>
							</tr>
							<c:forEach var="detail" items="${requestScope.detailList}">
							<tr>
								<td><c:out value='${detail.material}'/></td>
								<td align="right"><c:out value='${detail.length}'/></td>
								<td align="right"><c:out value='${detail.width}'/></td>
								<td align="right"><c:out value='${detail.quantity}'/></td>
								<td align="right"><c:out value='${detail.price}'/></td>
								<td><input type='button' onclick=delCurrentRow(this) value=" - "></td>
								<td width="0" style="display:none">
									<input type="hidden" name="did" value="<c:out value='${detail.id}'/>">
								</td>
							</tr>
							</c:forEach>
						</table>
					</fieldset>
				</td>
			</tr>
			<!-- --------- -->
			<tr>
				<td colspan="4">
					<fieldset>
						<legend>收费明细</legend>
						<table width="100%" id="payTable">
							<tr bgColor="#81bd77">
								<td width="20%">收款单号</td>
								<td width="20%">支付金额</td>
								<td width="10%">支付日期</td>
								<td width="20%">用途（定金或付款）</td>
								<td width="20%">收款人</td>
								<td width="10%" align="center">
									<input type="button" name="btnAdd" value=" + " onClick="addPay()">
								</td>
								<td width="0" style="display:none"></td>
							</tr>
							<c:forEach var="pay" items="${requestScope.payList}">
							<tr>
								<td><c:out value='${pay.payNo}'/></td>
								<td align="right"><c:out value='${pay.pay}'/></td>
								<td><c:out value='${pay.payDate}'/></td>
								<td><c:out value='${pay.type}'/></td>
								<td><c:out value='${pay.payee}'/></td>
								<td><input type='button' onclick=delCurrentRow(this) value=" - "></td>
								<td width="0" style="display:none">
									<input type="hidden" name="pid" value="<c:out value='${pay.id}'/>"> 
								</td>
							</tr>
							</c:forEach>
						</table>
					</fieldset>
					<table width="100%" style="border: 1px dashed #999999">
						<tr>
								<td width=:15%" class="label">余款未收</td>
								<td style="color:red"><c:out value='${project.cost-project.costPaid}'/></td>
							</tr>
					</table>
				</td>
			</tr>
			<!-- ------- -->
			<tr>
				<td class="buttonBar" colspan="4">
						<input type="button" style="width:80px" name="btnUpdate" value="修改" onClick="doUpdate()">
						<input type="button" style="width:80px" name="btnCancel" value="返回" onClick="goBack()">
				</td>
			</tr>	
	  </table>
	</h:form>
</body>
<script type="text/javascript">
		var f = document.forms(0);
	function doUpdate(){
		
		if(checkNotEmpty(f.name,"工程名称")==false){
			return ;
		}
		
		if(checkNotEmpty(f.processCost,"加工费")==false){
			return ;
		}
		
		if(checkNotEmpty(f.materialCost,"材料费")==false){
			return ;
		}
		
		if(checkNumber(f.materialCost,"材料费")==false){
			return;
		}
		
		if(checkNumber(f.processCost,"加工费")==false){
			return;
		}
		
		/**
		var payCol1 = document.all("payCol1");
		var payCol2 = document.all("payCol2");
		
		alert(payCol2.length);
		
		if(payCol1){		
		
			if(payCol1.length){
				for(var i=0;i<payCol1.length;i++){
					if(checkNotEmpty(payCol1[i],"支付金额")==false){
						return ;
					}
					if(checkNotEmpty(payCol2[i],"支付日期")==false){
						return ;
					}

				}
			}else{
				if(checkNotEmpty(payCol1,"支付金额")==false){
						return ;
					}
					if(checkNotEmpty(payCol2,"支付日期")==false){
						return ;
					}
					
					
			}
		}
		**/
		var payTable = document.getElementById("payTable");
		for(var i=1;i<payTable.rows.length;i++){
		
			var obj = payTable.rows[i].cells(1).childNodes[0];
			
			if(checkNotEmpty(obj,"支付金额")==false){
				return ;
			}
			
			obj = payTable.rows[i].cells(2).childNodes[0];
			if(checkNotEmpty(obj,"支付日期")==false){
				return ;
			}
			
		}
		
		f.submit();
	}
	
	function goBack(){
		window.location = "<%=request.getContextPath()%>/projectList.do";
	}
	
	
	function addDetail(){
		var detailTable = document.getElementById("detailTable");
		var rowObject = detailTable.insertRow();
		//材料
		var detailCol1 = document.createElement("<input type='text' name='detailCol1' width='100%'>");
		addTableItem(rowObject,detailCol1);
		//长
		var detailCol2 = document.createElement("<input type='text' name='detailCol2' width='100%' style='text-align:right'>");
		detailCol2.onblur=function (){checkNumber(detailCol2,'长度')};
		addTableItem(rowObject,detailCol2);
		//宽
		var detailCol3 = document.createElement("<input type='text' name='detailCol3' width='100%' style='text-align:right'>");
		addTableItem(rowObject,detailCol3);
		detailCol3.onblur=function (){checkNumber(detailCol3,'宽度')};
		//数量
		var detailCol4 = document.createElement("<input type='text' name='detailCol4' width='100%' style='text-align:right'>");
		addTableItem(rowObject,detailCol4);
		detailCol4.onblur=function (){checkNumber(detailCol4,'数量')};
		//单价
		var detailCol5 = document.createElement("<input type='text' name='detailCol5' width='100%' style='text-align:right'>");
		addTableItem(rowObject,detailCol5);
		detailCol5.onblur=function (){checkNumber(detailCol5,'单价')};
		//删除按钮
		var delButton = document.createElement("<input type=button onclick=delCurrentRow(this)>");
		delButton.value =' - ';			
		addTableItem(rowObject,delButton);	
		
		var did = document.createElement("<input type='hidden' name='did' value=''>");
		addTableItem(rowObject,did);
	}
	
	function addPay(){
		var payTable = document.getElementById("payTable");
		var rowObject = payTable.insertRow();
		
		//收款单号
		var payCol5 = document.createElement("<input type='text' name='payCol5' width='100%'>");
		payCol5.value=f.serrialNo.value;
		addTableItem(rowObject,payCol5);
		
		//支付金额
		var payCol1 = document.createElement("<input type='text' name='payCol1' width='100%' style='text-align:right'>");
		addTableItem(rowObject,payCol1);
		payCol1.onblur=function (){checkNumber(payCol1,'支付金额')};
		//支付日期
		var payCol2 = document.createElement("<input type='text' name='payCol2' size='10' readonly>");
		addTableItemWithDate(rowObject,payCol2);
		//用途
		var payCol3 = document.createElement("<input type='text' name='payCol3' width='100%'>");
		addTableItem(rowObject,payCol3);
		
		//收款人
		var payCol4 = document.createElement("<input type='text' name='payCol4' width='100%'>");
		addTableItem(rowObject,payCol4);
		
		
		//删除按钮
		var delButton = document.createElement("<input type='button' onclick=delCurrentRow(this)>");
		delButton.value =' - ';			
		addTableItem(rowObject,delButton);	
		
		var pid = document.createElement("<input type='hidden' name='pid' value=''>");
		addTableItem(rowObject,pid);
	}
	
	function addTableItem(rowObject,item){
		var cellObject = rowObject.insertCell();
		cellObject.appendChild(item);
		//alert(cellObject.innerHTML);
	}
	
	function addTableItemWithDate(rowObject,item){
		var cellObject = rowObject.insertCell();
		cellObject.appendChild(item);
		var img1 = document.createElement("<img src='/order/CalenderImages/calendar.gif'/>");
		img1.onclick=function(){popUpCalendar(img1,item,'yyyy-mm-dd')};
		var img2 = document.createElement("<img src='/order/CalenderImages/clear.gif'/>");
		img2.onclick=function (){item.value=''};
		cellObject.appendChild(img1);
		cellObject.appendChild(img2);
	}
	
	function delCurrentRow(obj){
		obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
	}
	
	function init(){
		var detailTable = document.getElementById("detailTable");
		var rows = detailTable.rows.length;
		for(var i=1;i<rows;i++){
			//detailTable.rows[i].title="单击修改数据";
			//材料
			createTextBox('detailCol1',detailTable.rows[i].cells[0]);
			//长度
			createNumberBox('detailCol2',detailTable.rows[i].cells[1]);
			//宽度
			createNumberBox('detailCol3',detailTable.rows[i].cells[2]);
			//数量
			createNumberBox('detailCol4',detailTable.rows[i].cells[3]);
			//单价
			createNumberBox('detailCol5',detailTable.rows[i].cells[4]);
			
		}
		//pay table
		var payTable = document.getElementById("payTable");
		 rows = payTable.rows.length;
		for(var i=1;i<rows;i++){
			//收款单号
			createTextBox('payCol5',payTable.rows[i].cells[0]);
			//金额
		 	createNumberBox('payCol1',payTable.rows[i].cells[1]);
		 	//支付日期
		 	createCalendarBox('payCol2',payTable.rows[i].cells[2]);
			//用途（定金或付款）
		 	createTextBox('payCol3',payTable.rows[i].cells[3]);
			//收款人
			createTextBox('payCol4',payTable.rows[i].cells[4]);
		}
		
	}
	
	
	function createTextBox(name,cell){
		var box = document.createElement("<input type='text' width='100%'>");
		box.name = name;
		box.value = cell.innerHTML;
		cell.innerHTML="";
		cell.appendChild(box);
		//return box;
	}
	
	function createNumberBox(name,cell){
		var box = document.createElement("<input type='text' width='100%' style='text-align:right'>");
		box.name = name;
		box.value =  cell.innerHTML;
		box.onblur=function(){
			var text = this.value;
			
			if(isNaN(text)){
				alert("只能输入数字！");
				this.focus();
				return;
			}

		}
		cell.innerHTML="";
		cell.appendChild(box);
	}
	
	function createCalendarBox(name,cell){
		//alert(name);
		var box = document.createElement("<input type='text' size='10' readonly name='"+name+"'>");
		//box.name = name;
		box.value = cell.innerHTML;
		cell.innerHTML="";
		cell.appendChild(box);
		
		var img1 = document.createElement("<img src='/order/CalenderImages/calendar.gif'/>");
		img1.onclick=function(){popUpCalendar(img1,box,'yyyy-mm-dd')};
		var img2 = document.createElement("<img src='/order/CalenderImages/clear.gif'/>");
		img2.onclick=function (){box.value=''};
		cell.appendChild(img1);
		cell.appendChild(img2);
	}
	
	window.onLoad=init();
</script>
</html>