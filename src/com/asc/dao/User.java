package com.asc.dao;

import javax.persistence.*;

import java.io.Serializable;

//ָ����������,ע�ⷽʽ

@SuppressWarnings("serial")
@Entity
@Table(name = "users2")
public class User implements Serializable {

	public Integer id;

	public String username;

	public String password;

	// ����,ע�ⷽʽ

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {

		return id;

	}

	public void setId(Integer id) {

		this.id = id;

	}

	public String getPassword() {

		return password;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public String getUsername() {

		return username;

	}

	public void setUsername(String username) {

		this.username = username;

	}

}
