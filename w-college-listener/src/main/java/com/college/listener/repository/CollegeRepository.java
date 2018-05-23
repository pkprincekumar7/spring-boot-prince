package com.college.listener.repository;

import org.springframework.data.repository.CrudRepository;

import com.college.listener.entity.College;

public interface CollegeRepository extends CrudRepository<College, Integer> {

}
