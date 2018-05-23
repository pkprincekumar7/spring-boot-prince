package com.teacher.repository;

import org.springframework.data.repository.CrudRepository;

import com.teacher.entity.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Integer> {

}
