package com.nology.java.consolidation.dtos;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.nology.java.consolidation.entities.Temp;


public class JobDTO {
	
	
	@NotNull
	public String name;
	
	@NotNull
	public LocalDate startDate;
	
	@NotNull
	public LocalDate endDate;
	
	public Long tId;
	
	public Temp temp;

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

	public Long getTid() {
		return tId;
	}
	public void setTid(Long tId) {
		this.tId = tId;
	}
	public Temp getTemp() {
		return temp;
	}

	public void setTemp(Temp temp) {
		this.temp = temp;
	}
	


	

	

	
	

	


	
	
}
