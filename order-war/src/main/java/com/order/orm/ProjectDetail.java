package com.order.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ProjectDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project_detail")
public class ProjectDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Project project;
	private Double width;
	private Double length;
	private String material;
	private Double price;
	private Integer quantity;
	private String note;

	// Constructors

	/** default constructor */
	public ProjectDetail() {
	}

	/** full constructor */
	public ProjectDetail(Project project, Double width, Double length,
			String material, Double price, Integer quantity, String note) {
		this.project = project;
		this.width = width;
		this.length = length;
		this.material = material;
		this.price = price;
		this.quantity = quantity;
		this.note = note;
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

	@Column(name = "width", precision = 11)
	public Double getWidth() {
		return this.width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	@Column(name = "length", precision = 11)
	public Double getLength() {
		return this.length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	@Column(name = "material", length = 200)
	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Column(name = "price", precision = 11)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "quantity")
	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "note", length = 1000)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}