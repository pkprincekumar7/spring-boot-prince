package com.teacher.repository;

import org.springframework.data.repository.CrudRepository;

import com.teacher.entity.Feedback;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

}
