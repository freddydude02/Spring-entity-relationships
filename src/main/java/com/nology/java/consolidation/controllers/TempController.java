package com.nology.java.consolidation.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nology.java.consolidation.dtos.TempDTO;
import com.nology.java.consolidation.entities.Temp;
import com.nology.java.consolidation.services.TempService;

@RestController
@RequestMapping(value = "/temp")
public class TempController {

	
	@Autowired
	private TempService tempService;
	
	@GetMapping
	public ResponseEntity<List<Temp>> getAllTemps(@RequestParam(required = false) Long jobid){
		
		if (jobid == null) {
			List<Temp> temp = tempService.getAllTemps();
			if (temp == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(temp, HttpStatus.OK);
		}
		else {
			List<Temp> temp = tempService.getAllAvailableTemps(jobid);
			if (temp == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(temp, HttpStatus.OK);
		}
	}
	
	@PostMapping
	public ResponseEntity<Temp> saveTemp(@Valid @RequestBody TempDTO tempData) {
		Temp temp = tempService.createTemp(tempData);
		return new ResponseEntity<>(temp,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Temp>> getTemp(@PathVariable Long id) {
		Optional<Temp> temp = tempService.getTemp(id);
		
		if (temp.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(temp,HttpStatus.OK);
	}
	
	@PatchMapping("/{tempId}")
	public ResponseEntity<Temp> updateTemp(@PathVariable Long tempId,@Valid @RequestBody TempDTO data) {
		Temp temp = tempService.updateTemp(tempId, data);
		if (temp == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(temp, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteTemp(@PathVariable Long id) {
		Boolean temp = tempService.deleteTemp(id);
		if (temp == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
	}
}
