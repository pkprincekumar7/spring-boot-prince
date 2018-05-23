package com.teacher.listener.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.listener.constants.CommonConstants;
import com.teacher.listener.consumer.Receiver;
import com.teacher.listener.email.service.EmailService;
import com.teacher.listener.model.TeacherModel;
import com.teacher.listener.model.User;
import com.teacher.listener.service.TeacherService;

public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	TeacherService teacherService;

	@Autowired
	private EmailService emailService;

	@KafkaListener(topics = "${kafka.topic.teachertopic}")
	public void receive(String payload) {
		LOGGER.info("received payload='{}'", payload);
		TeacherModel teacherModel = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			teacherModel = objectMapper.readValue(payload, TeacherModel.class);

			if (teacherModel.getId() != null
					&& CommonConstants.UPDATE_ACTION.equalsIgnoreCase(teacherModel.getAction())) {
				teacherService.updateTeacher(teacherModel.getId(), teacherModel);
				// fetch teacher detail from database to get email id
				teacherModel = teacherService.getTeacher(teacherModel.getId());
				this.sendEmail(teacherModel.getEmail(), teacherModel.getName(),
						"Your details updated successfully: " + this.getUpdatedDetails(teacherModel));

			} else if (CommonConstants.ADD_ACTION.equalsIgnoreCase(teacherModel.getAction())) {
				teacherService.addTeacher(teacherModel);
				// no need to fetch email because email field is mandatory for first time save
				this.sendEmail(teacherModel.getEmail(), teacherModel.getName(),
						"Registration successful: " + this.getUpdatedDetails(teacherModel));

			} else if (teacherModel.getId() != null
					&& CommonConstants.DELETE_ACTION.equalsIgnoreCase(teacherModel.getAction())) {
				// fetch teacher detail from database to get email id
				teacherModel = teacherService.getTeacher(teacherModel.getId());
				teacherService.deleteTeacher(teacherModel.getId());
				this.sendEmail(teacherModel.getEmail(), teacherModel.getName(),
						"Your details deleted successfully: " + this.getUpdatedDetails(teacherModel));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			// send failure message to the user
			this.sendEmail(teacherModel.getEmail(), teacherModel.getName(), "Failure in saving your details");
		}
	}

	public String getUpdatedDetails(TeacherModel teacherModel) {
		return "(ID: " + teacherModel.getId() + "), (Name: " + teacherModel.getName() + "), (City: "
				+ teacherModel.getCity() + "), (Pincode: " + teacherModel.getPinCode() + "), (Designation: "
				+ teacherModel.getDesignation() + "), (Email Id: " + teacherModel.getEmail() + ")";
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