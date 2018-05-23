package com.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.admin.entity.Feedback;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

}
