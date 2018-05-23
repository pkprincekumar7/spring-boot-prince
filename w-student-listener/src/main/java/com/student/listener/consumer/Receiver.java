package com.student.listener.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;

import com.student.listener.model.StudentModel;
import com.student.listener.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.listener.constants.CommonConstants;
import com.student.listener.email.service.EmailService;
import com.student.listener.model.User;

public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	StudentService studentService;

	@Autowired
	private EmailService emailService;

	@KafkaListener(topics = "${kafka.topic.studenttopic}")
	public void receive(String payload) {
		LOGGER.info("received payload='{}'", payload);
		StudentModel studentModel = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			studentModel = objectMapper.readValue(payload, StudentModel.class);

			if (studentModel.getId() != null
					&& CommonConstants.UPDATE_ACTION.equalsIgnoreCase(studentModel.getAction())) {
				studentService.updateStudent(studentModel.getId(), studentModel);
				// fetch student detail from database to get email id
				studentModel = studentService.getStudent(studentModel.getId());
				this.sendEmail(studentModel.getEmail(), studentModel.getName(),
						"Your details updated successfully: " + this.getUpdatedDetails(studentModel));

			} else if (CommonConstants.ADD_ACTION.equalsIgnoreCase(studentModel.getAction())) {
				studentService.addStudent(studentModel);
				// no need to fetch email because email field is mandatory for first time save
				this.sendEmail(studentModel.getEmail(), studentModel.getName(),
						"Registration successful: " + this.getUpdatedDetails(studentModel));

			} else if (studentModel.getId() != null
					&& CommonConstants.DELETE_ACTION.equalsIgnoreCase(studentModel.getAction())) {
				// fetch student detail from database to get email id
				studentModel = studentService.getStudent(studentModel.getId());
				studentService.deleteStudent(studentModel.getId());
				this.sendEmail(studentModel.getEmail(), studentModel.getName(),
						"Your details deleted successfully: " + this.getUpdatedDetails(studentModel));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			// send failure message to the user
			this.sendEmail(studentModel.getEmail(), studentModel.getName(), "Failure in saving your details");
		}
	}

	public String getUpdatedDetails(StudentModel studentModel) {
		return "(ID: " + studentModel.getId() + "), (Name: " + studentModel.getName() + "), (City: "
				+ studentModel.getCity() + "), (Pincode: " + studentModel.getPinCode() + "), (Standard: "
				+ studentModel.getStandard() + ")" + "), (Percentage Marks: " + studentModel.getPercentageMarks()
				+ "), (Email Id: " + studentModel.getEmail() + ")";
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