package com.nology.java.consolidation.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.nology.java.consolidation.entities.Temp;

public interface TempRepository  extends JpaRepository<Temp, Long>{
	
}
