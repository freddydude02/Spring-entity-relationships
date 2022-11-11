package com.nology.java.consolidation.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nology.java.consolidation.entities.User;
import com.nology.java.consolidation.repositories.UserRepository;
import com.nology.java.consolidation.security.UserDetailsImpl;


@Service
@Transactional
public class UserService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> fetchedUser = userRepository.findByUsername(username);
		if (fetchedUser.isEmpty()) {
            // User doesn't exist
            throw new UsernameNotFoundException(username);
        }
        User user = fetchedUser.get();
		return UserDetailsImpl.build(user);
	}

}
