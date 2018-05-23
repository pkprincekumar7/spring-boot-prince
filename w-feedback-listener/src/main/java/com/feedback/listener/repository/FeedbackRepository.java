package com.feedback.listener.repository;

import org.springframework.data.repository.CrudRepository;

import com.feedback.listener.entity.Feedback;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

}
