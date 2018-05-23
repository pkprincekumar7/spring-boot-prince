package com.teacher.listener.repository;

import org.springframework.data.repository.CrudRepository;

import com.teacher.listener.entity.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Integer> {

}
