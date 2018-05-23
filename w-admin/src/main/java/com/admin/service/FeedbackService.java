package com.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.admin.constants.CommonConstants;
import com.admin.entity.Feedback;
import com.admin.model.FeedbackModel;
import com.admin.repository.FeedbackRepository;

@Service
public class FeedbackService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);

	@Value("${kafka.producer.url}")
	private String kafkaProducerUrl;

	@Value("${kafka.topic.feedback}")
	private String kafkaFeedbackTopic;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private FeedbackRepository feedbackRepository;

	public List<FeedbackModel> getAllFeedbacks() {
		List<Feedback> feedbackList = new ArrayList<>();
		List<FeedbackModel> feedbackModels = new ArrayList<>();
		try {
			feedbackRepository.findAll().forEach(feedbackList::add);
			for (Feedback feedback : feedbackList) {
				feedbackModels.add(new FeedbackModel(feedback.getId(), feedback.getCandidateId(), feedback.getMessage(),
						feedback.getEmail()));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		return feedbackModels;
	}

	public FeedbackModel getFeedback(Integer id) {
		FeedbackModel feedbackModel = null;
		try {
			Feedback feedback = feedbackRepository.findOne(id);
			if (feedback != null) {
				feedbackModel = new FeedbackModel(feedback.getId(), feedback.getCandidateId(), feedback.getMessage(),
						feedback.getEmail());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		return feedbackModel;
	}

	public String addFeedback(FeedbackModel feedbackModel) {
		String url = kafkaProducerUrl + kafkaFeedbackTopic;
		String responseString = null;
		try {
			if (feedbackModel == null) {
				responseString = "Candidate Id, Message and Email fields are required";
			} else if (feedbackModel.getCandidateId() == null) {
				responseString = "Candidate Id field is required";
			} else if (feedbackModel.getCandidateId() <= 0) {
				responseString = "Candidate Id should be greater than zero";
			} else if (StringUtils.isBlank(feedbackModel.getMessage())) {
				responseString = "Message field is required";
			} else if (StringUtils.isBlank(feedbackModel.getEmail())) {
				responseString = "Email field is required";
			} else {
				feedbackModel.setAction(CommonConstants.ADD_ACTION);
				responseString = restTemplate.postForObject(url, feedbackModel, String.class);
			}
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}
}
