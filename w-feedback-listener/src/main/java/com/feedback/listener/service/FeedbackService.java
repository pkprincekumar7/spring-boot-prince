package com.feedback.listener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feedback.listener.entity.Feedback;
import com.feedback.listener.model.FeedbackModel;
import com.feedback.listener.repository.FeedbackRepository;

@Service
public class FeedbackService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);
	
	@Autowired
	private FeedbackRepository feedbackRepository;

	public FeedbackModel getFeedback(Integer id) {
		FeedbackModel feedbackModel = null;
		try {
			Feedback feedback = feedbackRepository.findOne(id);
			if (feedback != null) {
				feedbackModel = new FeedbackModel(
						feedback.getId(), 
						feedback.getCandidateId(),  
						feedback.getMessage(), 
						feedback.getEmail()
					);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return feedbackModel;
	}
	
	@SuppressWarnings("null")
	public FeedbackModel addFeedback(FeedbackModel feedbackModel) {		
		Feedback feedback = null;
		try {
			if (feedbackModel != null) {
				feedback = new Feedback(
						feedbackModel.getCandidateId(), 
						feedbackModel.getMessage(), 
						feedbackModel.getEmail()
					);
				
				feedbackRepository.save(feedback);
				feedbackModel.setId(feedback.getId());
				
			} else {
				feedbackModel.setId(null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return feedbackModel;
	}
}
