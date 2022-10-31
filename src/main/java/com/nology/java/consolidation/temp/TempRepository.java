package com.nology.java.consolidation.temp;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TempRepository  extends JpaRepository<Temp, Long>{
	Optional<Temp> findByUserName(String userName);
	
	Boolean existsByUserName(String userName);
	
}
