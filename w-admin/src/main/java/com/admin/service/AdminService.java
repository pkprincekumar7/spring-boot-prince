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
import com.admin.entity.Admin;
import com.admin.model.AdminModel;
import com.admin.repository.AdminRepository;

@Service
public class AdminService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
	
	@Value("${kafka.producer.url}")
	private String kafkaProducerUrl;
	
	@Value("${kafka.topic.admin}")
	private String kafkaAdminTopic;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AdminRepository adminRepository;

	public List<AdminModel> getAllAdmins() {
		List<Admin> adminList = new ArrayList<>();
		List<AdminModel> adminModels = new ArrayList<>();
		try {
			adminRepository.findAll().forEach(adminList::add);
			for (Admin admin : adminList) {
				adminModels.add(new AdminModel(
						admin.getId(), 
						admin.getName(), 
						admin.getCity(), 
						admin.getPinCode(), 
						admin.getDesignation(), 
						admin.getEmail(), 
						null
					));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return adminModels;
	}
	
	public AdminModel getAdmin(Integer id) {
		AdminModel adminModel = null;
		try {
			Admin admin = adminRepository.findOne(id);
			if (admin != null) {
				adminModel = new AdminModel(
						admin.getId(), 
						admin.getName(), 
						admin.getCity(), 
						admin.getPinCode(), 
						admin.getDesignation(), 
						admin.getEmail(), 
						null
					);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return adminModel;
	}
	
	public String addAdmin(AdminModel adminModel) {
		String url = kafkaProducerUrl + kafkaAdminTopic;
		String responseString = null;
		try {
			if (adminModel == null) {
				responseString = "Name, City, Pincode, Designation and Email fields are required";
			} else if (StringUtils.isBlank(adminModel.getName())) {
				responseString = "Name field is required";
			} else if (StringUtils.isBlank(adminModel.getCity())) {
				responseString = "City field is required";
			} else if (StringUtils.isBlank(adminModel.getPinCode())) {
				responseString = "Pin code field is required";
			} else if (StringUtils.isBlank(adminModel.getDesignation())) {
				responseString = "Designation field is required";
			} else if (StringUtils.isBlank(adminModel.getEmail())) {
				responseString = "Email field is required";
			} else {
				adminModel.setAction(CommonConstants.ADD_ACTION);
				responseString = restTemplate.postForObject(url, adminModel, String.class);
			}
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}
	
	public String updateAdmin(Integer id, AdminModel adminModel) {
		String url = kafkaProducerUrl + kafkaAdminTopic;
		String responseString = null;
		try {
			if (adminModel == null) {
				responseString = "At lease one of the fields (Name, City, Pincode, Designation, Email) is required";
			}
			else if (StringUtils.isBlank(adminModel.getName())
					&& StringUtils.isBlank(adminModel.getCity())
					&& StringUtils.isBlank(adminModel.getPinCode())
					&& StringUtils.isBlank(adminModel.getDesignation())
					&& StringUtils.isBlank(adminModel.getEmail())) {
				responseString = "At lease one of the fields (Name, City, Pincode, Designation, Email) is required";
			} else {
				adminModel.setId(id);
				adminModel.setAction(CommonConstants.UPDATE_ACTION);
				responseString = restTemplate.postForObject(url, adminModel, String.class);
			}
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}
	
	public String deleteAdmin(Integer id) {
		String url = kafkaProducerUrl + kafkaAdminTopic;
		String responseString = null;
		AdminModel adminModel = new AdminModel();
		try {
			adminModel.setId(id);
			adminModel.setAction(CommonConstants.DELETE_ACTION);
			responseString = restTemplate.postForObject(url, adminModel, String.class);
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}
}
