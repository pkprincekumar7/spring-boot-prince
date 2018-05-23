package com.admin.listener.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.listener.entity.Admin;
import com.admin.listener.model.AdminModel;
import com.admin.listener.repository.AdminRepository;

@Service
public class AdminService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);
	
	@Autowired
	private AdminRepository adminRepository;
	
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

	@SuppressWarnings("null")
	public void addAdmin(AdminModel adminModel) {
		Admin admin = null;
		try {
			if (adminModel != null) {
				admin = new Admin(
						adminModel.getName(), 
						adminModel.getCity(), 
						adminModel.getPinCode(), 
						adminModel.getDesignation(), 
						adminModel.getEmail()
					);
				
				adminRepository.save(admin);
				adminModel.setId(admin.getId());
				
			} else {
				adminModel.setId(null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
	}
	
	@SuppressWarnings("null")
	public void updateAdmin(Integer id, AdminModel adminModel) {
		Admin admin = null;
		try {
			admin = adminRepository.findOne(id);
			if (adminModel != null) {
				if (StringUtils.isNotBlank(adminModel.getName())) {
					admin.setName(adminModel.getName());
				}
				if (StringUtils.isNotBlank(adminModel.getCity())) {
					admin.setCity(adminModel.getCity());
				}
				if (StringUtils.isNotBlank(adminModel.getPinCode())) {
					admin.setPinCode(adminModel.getPinCode());
				}
				if (StringUtils.isNotBlank(adminModel.getDesignation())) {
					admin.setDesignation(adminModel.getDesignation());
				}
				if (StringUtils.isNotBlank(adminModel.getEmail())) {
					admin.setEmail(adminModel.getEmail());
				}
				
				adminRepository.save(admin);
				adminModel.setId(admin.getId());
				
			} else {
				adminModel.setId(null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
	}
	
	public Integer deleteAdmin(Integer id) {
		try {
			adminRepository.delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		return id;
	}
}
