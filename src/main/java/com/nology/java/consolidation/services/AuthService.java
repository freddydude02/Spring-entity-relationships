package com.nology.java.consolidation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nology.java.consolidation.dtos.UserLoginDTO;
import com.nology.java.consolidation.dtos.UserRegisterDTO;
import com.nology.java.consolidation.entities.User;
import com.nology.java.consolidation.repositories.UserRepository;
import com.nology.java.consolidation.security.UserDetailsImpl;
import com.nology.java.consolidation.security.jwt.JwtUtils;

@Service
@Transactional
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	public Object[] Login( UserLoginDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	
		
		List<String> role = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		Object[] returnObj = {
				jwt,userDetails.getId(), 
				userDetails.getUsername(), 
				role};
		
		return returnObj;
	}
	
	public User signUp(UserRegisterDTO signUpRequest) {
		if (userRepo.existsByUsername(signUpRequest.getUsername())) {
			return null;
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 encoder.encode(signUpRequest.getPassword()),
							 "User");

		userRepo.save(user);
		
		return user;
	}
}
