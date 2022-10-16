package com.nology.java.consolidation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/workers")
public class WorkerController {

	@Autowired
	private WorkerService workerService;
	
	@GetMapping("/temp")
	public List<Temp> getAllTemps(){
		return workerService.getAllTemps();
	}
	
	@GetMapping("/temp/job/{jobId}")
	public List<Temp> getAllAvailableTemps(@PathVariable Long jobId) {
		return workerService.getAllAvailableTemps(jobId);
	}
	
	@GetMapping("/job")
	public List<Job> getAllJobs(){
		return workerService.getAllJobs();
	}
	
	@GetMapping("/job/assigned={bool}")
	public List<Job> getAllJobsTF(@PathVariable Boolean bool){
		return workerService.getAllJobsTF(bool);
	}
	
	@PostMapping("/temp")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void saveTemp(@Valid @RequestBody TempDTO temp) {
		
		workerService.createTemp(temp);
	}
	
	@PostMapping("/job")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void saveJob(@Valid @RequestBody JobDTO job) {
		if(job.getStartDate().isBefore(job.getEndDate()) || job.getStartDate().isEqual(job.getEndDate())) {
			workerService.createJob(job);
		}
	}
	
	@GetMapping("/temp/{id}")
	public Temp getTemp(@PathVariable Long id) {
		return workerService.getTemp(id);
	}
	
	@GetMapping("/job/{id}")
	public Job getJob(@PathVariable Long id) {
		return workerService.getJob(id);
	}
	
	@PutMapping("/temp/{tempId}")
	public ResponseEntity<Temp> updateTemp(@PathVariable Long tempId,@RequestBody TempDTO data) {
		Temp temp = workerService.updateTemp(tempId, data);
		if (temp == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(temp, HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/job/{jobId}/temp/{tempId}")
	public ResponseEntity<Job> assignJob(@PathVariable Long jobId,@PathVariable Long tempId) {
		Job job = workerService.assignJob(jobId,tempId);
		if (job == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(job, HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/job/{jobId}")
	public ResponseEntity<Job> updateJob(@PathVariable Long jobId, @RequestBody JobDTO data) {
		Job job = workerService.updateJob(jobId, data);
		if (job == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(job, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/temp/{id}")
	public void deleteTemp(@PathVariable Long id) {
		workerService.deleteTemp(id);
	}
	@DeleteMapping("/job/{id}")
	public void deleteJob(@PathVariable Long id) {
		workerService.deleteJob(id);
	}
}
