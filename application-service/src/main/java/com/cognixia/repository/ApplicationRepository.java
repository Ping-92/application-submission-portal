package com.cognixia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	@Modifying
    @Query(
            value = "TRUNCATE TABLE application",
            nativeQuery = true
    )
    public void truncateApplicationTable();
}
