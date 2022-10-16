package com.nology.java.consolidation;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "temp_id")
	private Temp temp;

	public Job(String name, LocalDate startDate, LocalDate endDate, Temp temp) {
		this.setName(name);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setTemp(temp);
	}
	
	public Job() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Temp getTemp(Temp temp) {
		return temp;
	}
	public void setTemp(Temp temp) {
		this.temp = temp;
	}
	public Temp findTemp() {
		return temp;
	}
	

	
	

	

}
