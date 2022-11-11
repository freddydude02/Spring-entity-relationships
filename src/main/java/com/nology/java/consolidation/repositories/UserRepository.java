package com.nology.java.consolidation.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nology.java.consolidation.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	  Optional<User> findByUsername(String username);

	    Boolean existsByUsername(String username);

}
