package com.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.admin.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

}
