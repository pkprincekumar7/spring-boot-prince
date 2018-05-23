package com.student.listener.repository;

import org.springframework.data.repository.CrudRepository;

import com.student.listener.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

}
