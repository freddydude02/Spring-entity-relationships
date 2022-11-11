package com.nology.java.consolidation.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nology.java.consolidation.dtos.UserLoginDTO;
import com.nology.java.consolidation.dtos.UserRegisterDTO;
import com.nology.java.consolidation.entities.User;
import com.nology.java.consolidation.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginDTO loginRequest) {

		Object[] returnObj = authService.Login(loginRequest);
		
		return ResponseEntity.ok(returnObj);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDTO signUpRequest) {
		if (authService.signUp(signUpRequest) == null) {
			return ResponseEntity
					.badRequest()
					.body("Error: Username is already taken!");
		}
		
		User user = authService.signUp(signUpRequest);

		return ResponseEntity.ok(user);
	}
}
