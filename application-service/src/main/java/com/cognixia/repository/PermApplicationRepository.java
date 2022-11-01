package com.cognixia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.model.PermApplication;

@Repository
public interface PermApplicationRepository extends JpaRepository<PermApplication, Integer>{

}
