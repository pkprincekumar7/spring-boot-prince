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
import com.admin.entity.College;
import com.admin.model.CollegeModel;
import com.admin.repository.CollegeRepository;

@Service
public class CollegeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CollegeService.class);
	
	@Value("${kafka.producer.url}")
	private String kafkaProducerUrl;
	
	@Value("${kafka.topic.college}")
	private String kafkaCollegeTopic;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CollegeRepository collegeRepository;

	public List<CollegeModel> getAllColleges() {
		List<College> collegeList = new ArrayList<>();
		List<CollegeModel> collegeModels = new ArrayList<>();
		try {
			collegeRepository.findAll().forEach(collegeList::add);
			for (College college : collegeList) {
				collegeModels.add(new CollegeModel(
						college.getId(), 
						college.getName(), 
						college.getCity(), 
						college.getPinCode(), 
						college.getMinStandard(),
						college.getMaxStandard(), 
						college.getEmail(), 
						null
					));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return collegeModels;
	}
	
	public CollegeModel getCollege(Integer id) {
		CollegeModel collegeModel = null;
		try {
			College college = collegeRepository.findOne(id);
			if (college != null) {
				collegeModel = new CollegeModel(
						college.getId(), 
						college.getName(), 
						college.getCity(), 
						college.getPinCode(), 
						college.getMinStandard(),
						college.getMaxStandard(), 
						college.getEmail(), 
						null
					);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return collegeModel;
	}
	
	public String addCollege(CollegeModel collegeModel) {
		String url = kafkaProducerUrl + kafkaCollegeTopic;
		String responseString = null;
		try {
			if (collegeModel == null) {
				responseString = "Name, City, Pincode, MinStandard, MaxStandard and Email fields are required";
			} else if (StringUtils.isBlank(collegeModel.getName())) {
				responseString = "Name field is required";
			} else if (StringUtils.isBlank(collegeModel.getCity())) {
				responseString = "City field is required";
			} else if (StringUtils.isBlank(collegeModel.getPinCode())) {
				responseString = "Pin code field is required";
			} else if (StringUtils.isBlank(collegeModel.getMinStandard())) {
				responseString = "MinStandard field is required";
			} else if (StringUtils.isBlank(collegeModel.getMaxStandard())) {
				responseString = "MaxStandard field is required";
			} else if (StringUtils.isBlank(collegeModel.getEmail())) {
				responseString = "Email field is required";
			} else {
				collegeModel.setAction(CommonConstants.ADD_ACTION);
				responseString = restTemplate.postForObject(url, collegeModel, String.class);
			}
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}
	
	public String updateCollege(Integer id, CollegeModel collegeModel) {
		String url = kafkaProducerUrl + kafkaCollegeTopic;
		String responseString = null;
		try {
			if (collegeModel == null) {
				responseString = "At lease one of the fields (Name, City, Pincode, MinStandard, MaxStandard, Email) is required";
			}
			else if (StringUtils.isBlank(collegeModel.getName())
					&& StringUtils.isBlank(collegeModel.getCity())
					&& StringUtils.isBlank(collegeModel.getPinCode())
					&& StringUtils.isBlank(collegeModel.getMinStandard())
					&& StringUtils.isBlank(collegeModel.getMaxStandard())
					&& StringUtils.isBlank(collegeModel.getEmail())) {
				responseString = "At lease one of the fields (Name, City, Pincode, MinStandard, MaxStandard Email) is required";
			} else {
				collegeModel.setId(id);
				collegeModel.setAction(CommonConstants.UPDATE_ACTION);
				responseString = restTemplate.postForObject(url, collegeModel, String.class);
			}
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}
	
	public String deleteCollege(Integer id) {
		String url = kafkaProducerUrl + kafkaCollegeTopic;
		String responseString = null;
		CollegeModel collegeModel = new CollegeModel();
		try {
			collegeModel.setId(id);
			collegeModel.setAction(CommonConstants.DELETE_ACTION);
			responseString = restTemplate.postForObject(url, collegeModel, String.class);
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}
}


