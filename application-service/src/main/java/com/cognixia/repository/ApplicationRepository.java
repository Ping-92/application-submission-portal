package com.cognixia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	
}
