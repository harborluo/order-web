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
	<title>��K���ʯ��-�޸�����</title>
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
			font-family:"����";
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
				<td colspan="4" class="bar">�޸�����</td>
			</tr>	
			<tr>
				<td width="15%" class="label">��������</td>
				<td width="35%"><h:input path="name" cssStyle="width:100%"/></td>
				<td width="15%" class="label">��������</td>
				<td width="35%">
					<h:input path="projectDate" size="10"/>
					<harbor:calendar property="projectDate"/>
				</td>
			</tr>
			<tr>
				<td class="label">�����ܼ�</td>
				<td><h:input path="cost" cssStyle="text-align:right" readonly="true"/></td>
				<td class="label">���տ�</td>
				<td><h:input path="costPaid" cssStyle="text-align:right" readonly="true"/></td>
			</tr>
			<tr>
				<td class="label">�ӹ���</td>
				<td><h:input path="processCost" cssStyle="text-align:right"/></td>
				<td class="label">���Ϸ�</td>
				<td><h:input path="materialCost" cssStyle="text-align:right"/></td>
			</tr>			
			<tr>
				<td class="label">��������</td>
				<td><h:input path="beginDate" size="10"/><harbor:calendar property="beginDate"/></td>
				<td class="label">�깤����</td>
				<td><h:input path="finishDate" size="10"/><harbor:calendar property="finishDate"/></td>
			</tr>
			<tr>
				<td class="label">����</td>
				<td><h:input path="serrialNo"/></td>
				<td class="label">�ͻ�</td>
				<td><h:input path="clientName"/></td>
			</tr>
			<tr>
				<td class="label">�ͻ��绰</td>
				<td><h:input path="clientPhone" cssStyle="width:100%"/></td>
				<td class="label">�ͻ���ַ</td>
				<td><h:input path="clientAddress" cssStyle="width:100%"/></td>
			</tr>
			<tr>
				<td class="label">��ע</td>
				<td colspan="3"><h:textarea path="note" cols="70" cssClass="input" rows="3"/></td>
			</tr>
			<!-- --------- -->
			<tr>
				<td colspan="4">
					<fieldset>
						<legend>��Ŀ��ϸ</legend>
						<table width="100%" id="detailTable">
							<tr bgColor="#81bd77">
								<td width="25%">����</td>
								<td width="15%">����</td>
								<td width="15%">���</td>
								<td width="15%">����</td>
								<td width="15%">����</td>
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
						<legend>�շ���ϸ</legend>
						<table width="100%" id="payTable">
							<tr bgColor="#81bd77">
								<td width="20%">�տ��</td>
								<td width="20%">֧�����</td>
								<td width="10%">֧������</td>
								<td width="20%">��;������򸶿</td>
								<td width="20%">�տ���</td>
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
								<td width=:15%" class="label">���δ��</td>
								<td style="color:red"><c:out value='${project.cost-project.costPaid}'/></td>
							</tr>
					</table>
				</td>
			</tr>
			<!-- ------- -->
			<tr>
				<td class="buttonBar" colspan="4">
						<input type="button" style="width:80px" name="btnUpdate" value="�޸�" onClick="doUpdate()">
						<input type="button" style="width:80px" name="btnCancel" value="����" onClick="goBack()">
				</td>
			</tr>	
	  </table>
	</h:form>
</body>
<script type="text/javascript">
		var f = document.forms(0);
	function doUpdate(){
		
		if(checkNotEmpty(f.name,"��������")==false){
			return ;
		}
		
		if(checkNotEmpty(f.processCost,"�ӹ���")==false){
			return ;
		}
		
		if(checkNotEmpty(f.materialCost,"���Ϸ�")==false){
			return ;
		}
		
		if(checkNumber(f.materialCost,"���Ϸ�")==false){
			return;
		}
		
		if(checkNumber(f.processCost,"�ӹ���")==false){
			return;
		}
		
		/**
		var payCol1 = document.all("payCol1");
		var payCol2 = document.all("payCol2");
		
		alert(payCol2.length);
		
		if(payCol1){		
		
			if(payCol1.length){
				for(var i=0;i<payCol1.length;i++){
					if(checkNotEmpty(payCol1[i],"֧�����")==false){
						return ;
					}
					if(checkNotEmpty(payCol2[i],"֧������")==false){
						return ;
					}

				}
			}else{
				if(checkNotEmpty(payCol1,"֧�����")==false){
						return ;
					}
					if(checkNotEmpty(payCol2,"֧������")==false){
						return ;
					}
					
					
			}
		}
		**/
		var payTable = document.getElementById("payTable");
		for(var i=1;i<payTable.rows.length;i++){
		
			var obj = payTable.rows[i].cells(1).childNodes[0];
			
			if(checkNotEmpty(obj,"֧�����")==false){
				return ;
			}
			
			obj = payTable.rows[i].cells(2).childNodes[0];
			if(checkNotEmpty(obj,"֧������")==false){
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
		//����
		var detailCol1 = document.createElement("<input type='text' name='detailCol1' width='100%'>");
		addTableItem(rowObject,detailCol1);
		//��
		var detailCol2 = document.createElement("<input type='text' name='detailCol2' width='100%' style='text-align:right'>");
		detailCol2.onblur=function (){checkNumber(detailCol2,'����')};
		addTableItem(rowObject,detailCol2);
		//��
		var detailCol3 = document.createElement("<input type='text' name='detailCol3' width='100%' style='text-align:right'>");
		addTableItem(rowObject,detailCol3);
		detailCol3.onblur=function (){checkNumber(detailCol3,'���')};
		//����
		var detailCol4 = document.createElement("<input type='text' name='detailCol4' width='100%' style='text-align:right'>");
		addTableItem(rowObject,detailCol4);
		detailCol4.onblur=function (){checkNumber(detailCol4,'����')};
		//����
		var detailCol5 = document.createElement("<input type='text' name='detailCol5' width='100%' style='text-align:right'>");
		addTableItem(rowObject,detailCol5);
		detailCol5.onblur=function (){checkNumber(detailCol5,'����')};
		//ɾ����ť
		var delButton = document.createElement("<input type=button onclick=delCurrentRow(this)>");
		delButton.value =' - ';			
		addTableItem(rowObject,delButton);	
		
		var did = document.createElement("<input type='hidden' name='did' value=''>");
		addTableItem(rowObject,did);
	}
	
	function addPay(){
		var payTable = document.getElementById("payTable");
		var rowObject = payTable.insertRow();
		
		//�տ��
		var payCol5 = document.createElement("<input type='text' name='payCol5' width='100%'>");
		payCol5.value=f.serrialNo.value;
		addTableItem(rowObject,payCol5);
		
		//֧�����
		var payCol1 = document.createElement("<input type='text' name='payCol1' width='100%' style='text-align:right'>");
		addTableItem(rowObject,payCol1);
		payCol1.onblur=function (){checkNumber(payCol1,'֧�����')};
		//֧������
		var payCol2 = document.createElement("<input type='text' name='payCol2' size='10' readonly>");
		addTableItemWithDate(rowObject,payCol2);
		//��;
		var payCol3 = document.createElement("<input type='text' name='payCol3' width='100%'>");
		addTableItem(rowObject,payCol3);
		
		//�տ���
		var payCol4 = document.createElement("<input type='text' name='payCol4' width='100%'>");
		addTableItem(rowObject,payCol4);
		
		
		//ɾ����ť
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
			//detailTable.rows[i].title="�����޸�����";
			//����
			createTextBox('detailCol1',detailTable.rows[i].cells[0]);
			//����
			createNumberBox('detailCol2',detailTable.rows[i].cells[1]);
			//���
			createNumberBox('detailCol3',detailTable.rows[i].cells[2]);
			//����
			createNumberBox('detailCol4',detailTable.rows[i].cells[3]);
			//����
			createNumberBox('detailCol5',detailTable.rows[i].cells[4]);
			
		}
		//pay table
		var payTable = document.getElementById("payTable");
		 rows = payTable.rows.length;
		for(var i=1;i<rows;i++){
			//�տ��
			createTextBox('payCol5',payTable.rows[i].cells[0]);
			//���
		 	createNumberBox('payCol1',payTable.rows[i].cells[1]);
		 	//֧������
		 	createCalendarBox('payCol2',payTable.rows[i].cells[2]);
			//��;������򸶿
		 	createTextBox('payCol3',payTable.rows[i].cells[3]);
			//�տ���
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
				alert("ֻ���������֣�");
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