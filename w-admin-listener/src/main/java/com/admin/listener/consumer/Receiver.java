package com.admin.listener.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;

import com.admin.listener.constants.CommonConstants;
import com.admin.listener.email.service.EmailService;
import com.admin.listener.model.AdminModel;
import com.admin.listener.model.User;
import com.admin.listener.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	AdminService adminService;

	@Autowired
	private EmailService emailService;

	@KafkaListener(topics = "${kafka.topic.admintopic}")
	public void receive(String payload) {
		LOGGER.info("received payload='{}'", payload);
		AdminModel adminModel = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			adminModel = objectMapper.readValue(payload, AdminModel.class);

			if (adminModel.getId() != null && CommonConstants.UPDATE_ACTION.equalsIgnoreCase(adminModel.getAction())) {
				adminService.updateAdmin(adminModel.getId(), adminModel);
				// fetch admin detail from database to get email id
				adminModel = adminService.getAdmin(adminModel.getId());
				this.sendEmail(adminModel.getEmail(), adminModel.getName(),
						"Your details updated successfully: " + this.getUpdatedDetails(adminModel));

			} else if (CommonConstants.ADD_ACTION.equalsIgnoreCase(adminModel.getAction())) {
				adminService.addAdmin(adminModel);
				// no need to fetch email because email field is mandatory for first time save
				this.sendEmail(adminModel.getEmail(), adminModel.getName(),
						"Registration successful: " + this.getUpdatedDetails(adminModel));

			} else if (adminModel.getId() != null
					&& CommonConstants.DELETE_ACTION.equalsIgnoreCase(adminModel.getAction())) {
				// fetch admin detail from database to get email id
				adminModel = adminService.getAdmin(adminModel.getId());
				adminService.deleteAdmin(adminModel.getId());
				this.sendEmail(adminModel.getEmail(), adminModel.getName(),
						"Your details deleted successfully: " + this.getUpdatedDetails(adminModel));
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			// send failure message to the user
			this.sendEmail(adminModel.getEmail(), adminModel.getName(), "Failure in saving your details");
		}
	}

	public String getUpdatedDetails(AdminModel adminModel) {
		return "(ID: " + adminModel.getId() + "), (Name: " + adminModel.getName() + "), (City: " + adminModel.getCity()
				+ "), (Pincode: " + adminModel.getPinCode() + "), (Designation: " + adminModel.getDesignation() + ")"
				+ "), (Email Id: " + adminModel.getEmail() + ")";
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