package com.nology.java.consolidation.job;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/job")
public class JobController {

	@Autowired
	private JobService JobService;

	
	@GetMapping
	public List<Job> getAllJobs(@RequestParam(required = false) Boolean bool){
		System.out.println(bool);
		if (bool == null) return JobService.getAllJobs();
		return JobService.getAllJobsTF(bool);
	}

	@PostMapping
	public ResponseEntity<Job> saveJob(@Valid @RequestBody JobDTO jobData) {
		if(jobData.getStartDate().isAfter(jobData.getEndDate())) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		Job job = JobService.createJob(jobData);
		return new ResponseEntity<>(job, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Job>> getJob(@PathVariable Long id) {
		Optional<Job> job = JobService.getJob(id);
		
		if (Optional.empty().equals(job)) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(job, HttpStatus.OK);
	}
	
	@PatchMapping("/{jobId}")
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
