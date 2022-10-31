package com.nology.java.consolidation.temp;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nology.java.consolidation.job.Job;

public class TempDTO {

	@NotNull
	@NotBlank
	public String FirstName;
	
	@NotNull
	@NotBlank
	public String LastName;
	
	public List<Job> jobArr;
	
	@NotNull
	@NotBlank
	public String userName;
	
	@NotNull
	@NotBlank
	public String passWord;


	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
	public List<Job> getJobArr() {
		return jobArr;
	}
	public void setJobArr(List<Job> jobArr) {
		this.jobArr = jobArr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	

	
	
	
	
}
