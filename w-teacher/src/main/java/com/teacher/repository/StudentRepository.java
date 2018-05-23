package com.teacher.repository;

import org.springframework.data.repository.CrudRepository;

import com.teacher.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

}
