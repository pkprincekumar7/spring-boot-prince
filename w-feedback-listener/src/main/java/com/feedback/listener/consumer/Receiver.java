package com.feedback.listener.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feedback.listener.constants.CommonConstants;
import com.feedback.listener.consumer.Receiver;
import com.feedback.listener.email.service.EmailService;
import com.feedback.listener.model.FeedbackModel;
import com.feedback.listener.model.User;
import com.feedback.listener.service.FeedbackService;

public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	FeedbackService feedbackService;

	@Autowired
	private EmailService emailService;

	@KafkaListener(topics = "${kafka.topic.feedbacktopic}")
	public void receive(String payload) {
		LOGGER.info("received payload='{}'", payload);
		FeedbackModel feedbackModel = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			feedbackModel = objectMapper.readValue(payload, FeedbackModel.class);
			if (CommonConstants.ADD_ACTION.equalsIgnoreCase(feedbackModel.getAction())) {
				feedbackService.addFeedback(feedbackModel);
				// no need to fetch email because email field is mandatory for first time save
				this.sendEmail(feedbackModel.getEmail(), feedbackModel.getCandidateId().toString(),
						"Feedback saved successfully: " + this.getUpdatedDetails(feedbackModel));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			// send failure message to the user
			this.sendEmail(feedbackModel.getEmail(), feedbackModel.getCandidateId().toString(),
					"Failure in saving your feedback");
		}
	}

	public String getUpdatedDetails(FeedbackModel feedbackModel) {
		return "(ID: " + feedbackModel.getId() + "), (Candidate Id: " + feedbackModel.getCandidateId() + "), (Message: "
				+ feedbackModel.getMessage() + "), (Email Id: " + feedbackModel.getEmail() + ")";
	}

	public void sendEmail(String emailId, String name, String message) {
		try {
			emailService.sendNotification(this.getUserInfo(emailId, name, message));
		} catch (MailException e1) {
			LOGGER.error(e1.getMessage());
		} catch (Exception e1) {
			LOGGER.error(e1.getMessage());
		}
	}

	public User getUserInfo(String emailId, String name, String message) {
		User user = new User();
		user.setName(name);
		user.setEmail(emailId);
		user.setSubject(message);
		user.setMessageBody(message);
		return user;
	}
}