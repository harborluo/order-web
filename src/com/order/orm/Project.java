package com.order.orm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Project entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project", catalog = "order")
public class Project implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Date projectDate;
	private Double processCost;
	private Double materialCost;
	private Double cost;
	private String serrialNo;
	private Double costPaid;
	private Date beginDate;
	private Date finishDate;
	private String note;
	private String clientName;
	private String clientPhone;
	private String clientAddress;
	private Set<ProjectDetail> projectDetails = new HashSet<ProjectDetail>(0);
	private Set<ProjectPay> projectPaies = new HashSet<ProjectPay>(0);

	// Constructors

	/** default constructor */
	public Project() {
	}

	/** full constructor */
	public Project(String name, Date projectDate, Double processCost,
			Double materialCost, Double cost, String serrialNo,
			Double costPaid, Date beginDate, Date finishDate, String note,
			String clientName, String clientPhone, String clientAddress,
			Set<ProjectDetail> projectDetails, Set<ProjectPay> projectPaies) {
		this.name = name;
		this.projectDate = projectDate;
		this.processCost = processCost;
		this.materialCost = materialCost;
		this.cost = cost;
		this.serrialNo = serrialNo;
		this.costPaid = costPaid;
		this.beginDate = beginDate;
		this.finishDate = finishDate;
		this.note = note;
		this.clientName = clientName;
		this.clientPhone = clientPhone;
		this.clientAddress = clientAddress;
		this.projectDetails = projectDetails;
		this.projectPaies = projectPaies;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "project_date", length = 0)
	public Date getProjectDate() {
		return this.projectDate;
	}

	public void setProjectDate(Date projectDate) {
		this.projectDate = projectDate;
	}

	@Column(name = "process_cost", precision = 11)
	public Double getProcessCost() {
		return this.processCost;
	}

	public void setProcessCost(Double processCost) {
		this.processCost = processCost;
	}

	@Column(name = "material_cost", precision = 11)
	public Double getMaterialCost() {
		return this.materialCost;
	}

	public void setMaterialCost(Double materialCost) {
		this.materialCost = materialCost;
	}

	@Column(name = "cost", precision = 11)
	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Column(name = "serrial_no", length = 50)
	public String getSerrialNo() {
		return this.serrialNo;
	}

	public void setSerrialNo(String serrialNo) {
		this.serrialNo = serrialNo;
	}

	@Column(name = "cost_paid", precision = 11)
	public Double getCostPaid() {
		return this.costPaid;
	}

	public void setCostPaid(Double costPaid) {
		this.costPaid = costPaid;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "begin_date", length = 0)
	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "finish_date", length = 0)
	public Date getFinishDate() {
		return this.finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Column(name = "note", length = 800)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "client_name", length = 200)
	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Column(name = "client_phone", length = 20)
	public String getClientPhone() {
		return this.clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	@Column(name = "client_address", length = 300)
	public String getClientAddress() {
		return this.clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<ProjectDetail> getProjectDetails() {
		return this.projectDetails;
	}

	public void setProjectDetails(Set<ProjectDetail> projectDetails) {
		this.projectDetails = projectDetails;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<ProjectPay> getProjectPaies() {
		return this.projectPaies;
	}

	public void setProjectPaies(Set<ProjectPay> projectPaies) {
		this.projectPaies = projectPaies;
	}

}