package com.college.listener.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;

import com.college.listener.constants.CommonConstants;
import com.college.listener.email.service.EmailService;
import com.college.listener.model.CollegeModel;
import com.college.listener.model.User;
import com.college.listener.service.CollegeService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	CollegeService collegeService;

	@Autowired
	private EmailService emailService;

	@KafkaListener(topics = "${kafka.topic.collegetopic}")
	public void receive(String payload) {
		LOGGER.info("received payload='{}'", payload);
		CollegeModel collegeModel = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			collegeModel = objectMapper.readValue(payload, CollegeModel.class);

			if (collegeModel.getId() != null
					&& CommonConstants.UPDATE_ACTION.equalsIgnoreCase(collegeModel.getAction())) {
				collegeService.updateCollege(collegeModel.getId(), collegeModel);
				// fetch college detail from database to get email id
				collegeModel = collegeService.getCollege(collegeModel.getId());
				this.sendEmail(collegeModel.getEmail(), collegeModel.getName(),
						"Your details updated successfully: " + this.getUpdatedDetails(collegeModel));

			} else if (CommonConstants.ADD_ACTION.equalsIgnoreCase(collegeModel.getAction())) {
				collegeService.addCollege(collegeModel);
				// no need to fetch email because email field is mandatory for first time save
				this.sendEmail(collegeModel.getEmail(), collegeModel.getName(),
						"Registration successful: " + this.getUpdatedDetails(collegeModel));

			} else if (collegeModel.getId() != null
					&& CommonConstants.DELETE_ACTION.equalsIgnoreCase(collegeModel.getAction())) {
				// fetch college detail from database to get email id
				collegeModel = collegeService.getCollege(collegeModel.getId());
				collegeService.deleteCollege(collegeModel.getId());
				this.sendEmail(collegeModel.getEmail(), collegeModel.getName(),
						"Your details deleted successfully: " + this.getUpdatedDetails(collegeModel));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			// send failure message to the user
			this.sendEmail(collegeModel.getEmail(), collegeModel.getName(), "Failure in saving your details");
		}
	}

	public String getUpdatedDetails(CollegeModel collegeModel) {
		return "(ID: " + collegeModel.getId() + "), (College Name: " + collegeModel.getName() + "), (City: "
				+ collegeModel.getCity() + "), (Pincode: " + collegeModel.getPinCode() + "), (Minimum Standard: "
				+ collegeModel.getMinStandard() + ")" + "), (Maximum Standard: " + collegeModel.getMaxStandard()
				+ "), (Email Id: " + collegeModel.getEmail() + ")";
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