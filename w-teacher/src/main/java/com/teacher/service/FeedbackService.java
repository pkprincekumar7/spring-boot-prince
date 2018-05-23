package com.teacher.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.teacher.constants.CommonConstants;
import com.teacher.model.FeedbackModel;

@Service
public class FeedbackService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);

	@Value("${kafka.producer.url}")
	private String kafkaProducerUrl;

	@Value("${kafka.topic.feedback}")
	private String kafkaFeedbackTopic;

	@Autowired
	private RestTemplate restTemplate;

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
