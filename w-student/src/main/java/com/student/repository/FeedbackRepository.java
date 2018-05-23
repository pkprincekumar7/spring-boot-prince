package com.student.repository;

import org.springframework.data.repository.CrudRepository;

import com.student.entity.Feedback;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

}
