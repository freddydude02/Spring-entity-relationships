package com.nology.java.consolidation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nology.java.consolidation.entities.Job;

public interface JobRepository extends JpaRepository<Job, Long>{

}
