package com.nology.java.consolidation.entities;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Temp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	
	@OneToMany(mappedBy = "temp", cascade = CascadeType.ALL)
	private List<Job> jobArr;

	public Temp(String firstName, String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public Temp() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Job> getJobArr() {
		return jobArr;
	}
	public void setJobArr(List<Job> jobArr) {
		this.jobArr = jobArr;
	}

	
	
	
}
