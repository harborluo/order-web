package com.order.controller;

import java.util.Date;

public class ProjectForm {
	private Integer id;
	private String name;
	private Date projectDate;
	private Double cost;
	private Double costPaid;
	private Date beginDate;
	private Date finishDate;
	private String note;
	private String clientName;
	private String clientPhone;
	private String clientAddress;
	private String serrialNo;
	private Double processCost;
	private Double materialCost;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getProjectDate() {
		return projectDate;
	}
	public void setProjectDate(Date projectDate) {
		this.projectDate = projectDate;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getCostPaid() {
		return costPaid;
	}
	public void setCostPaid(Double costPaid) {
		this.costPaid = costPaid;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientPhone() {
		return clientPhone;
	}
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}
	public String getClientAddress() {
		return clientAddress;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public String getSerrialNo() {
		return serrialNo;
	}
	public void setSerrialNo(String serrialNo) {
		this.serrialNo = serrialNo;
	}
	public Double getProcessCost() {
		return processCost;
	}
	public void setProcessCost(Double processCost) {
		this.processCost = processCost;
	}
	public Double getMaterialCost() {
		return materialCost;
	}
	public void setMaterialCost(Double materialCost) {
		this.materialCost = materialCost;
	}
	
}
