package com.nology.java.consolidation.job;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/job")
public class JobController {

	@Autowired
	private JobService JobService;

	
	@GetMapping()
	public List<Job> getAllJobs(){
		return JobService.getAllJobs();
	}
	
	@GetMapping("/assigned={bool}")
	public List<Job> getAllJobsTF(@PathVariable Boolean bool){
		return JobService.getAllJobsTF(bool);
	}

	
	@PostMapping()
	@ResponseStatus(value = HttpStatus.CREATED)
	public void saveJob(@Valid @RequestBody JobDTO job) {
		if(job.getStartDate().isBefore(job.getEndDate()) || job.getStartDate().isEqual(job.getEndDate())) {
			JobService.createJob(job);
		}
	}

	
	@GetMapping("/{id}")
	public Job getJob(@PathVariable Long id) {
		return JobService.getJob(id);
	}
	
	@PutMapping("/{jobId}")
	public ResponseEntity<Job> assignJob(@PathVariable Long jobId,@RequestBody JobDTO data) {
		Job job = JobService.updateJob(jobId,data);
		if (job == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(job, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{id}")
	public void deleteJob(@PathVariable Long id) {
		JobService.deleteJob(id);
	}
}
