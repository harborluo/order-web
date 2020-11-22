package com.order.orm;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProjectPay entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project_pay")
public class ProjectPay implements java.io.Serializable {

	// Fields

	private Integer id;
	private Project project;
	private Double pay;
	private Date payDate;
	private String type;
	private String payee;
	private String payNo;

	// Constructors

	/** default constructor */
	public ProjectPay() {
	}

	/** full constructor */
	public ProjectPay(Project project, Double pay, Date payDate, String type,
			String payee, String payNo) {
		this.project = project;
		this.pay = pay;
		this.payDate = payDate;
		this.type = type;
		this.payee = payee;
		this.payNo = payNo;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "pay", precision = 11)
	public Double getPay() {
		return this.pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "pay_date", length = 0)
	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Column(name = "type", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "payee", length = 50)
	public String getPayee() {
		return this.payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	@Column(name = "pay_no", length = 30)
	public String getPayNo() {
		return this.payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

}