package com.nology.java.consolidation;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;


public class JobDTO {
	
	
	@NotNull
	public String name;
	
	@NotNull
	public LocalDate startDate;
	
	@NotNull
	public LocalDate endDate;
	
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

	public Temp getTemp() {
		return temp;
	}

	public void setTemp(Temp temp) {
		this.temp = temp;
	}
	


	

	

	
	

	


	
	
}
