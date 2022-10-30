package com.cognixia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

}
