package com.nology.java.consolidation.temp;

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
@RequestMapping(value = "/temp")
public class TempController {

	
	@Autowired
	private TempService tempService;
	
	@GetMapping()
	public List<Temp> getAllTemps(){
		return tempService.getAllTemps();
	}
	
	@GetMapping("/job/{jobId}")
	public List<Temp> getAllAvailableTemps(@PathVariable Long jobId) {
		return tempService.getAllAvailableTemps(jobId);
	}
	
	@PostMapping()
	@ResponseStatus(value = HttpStatus.CREATED)
	public void saveTemp(@Valid @RequestBody TempDTO temp) {
		
		tempService.createTemp(temp);
	}
	
	@GetMapping("/{id}")
	public Temp getTemp(@PathVariable Long id) {
		return tempService.getTemp(id);
	}
	
	@PutMapping("/{tempId}")
	public ResponseEntity<Temp> updateTemp(@PathVariable Long tempId,@RequestBody TempDTO data) {
		Temp temp = tempService.updateTemp(tempId, data);
		if (temp == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(temp, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public void deleteTemp(@PathVariable Long id) {
		tempService.deleteTemp(id);
	}
}
