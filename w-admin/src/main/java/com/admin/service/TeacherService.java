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
import com.admin.entity.Teacher;
import com.admin.model.TeacherModel;
import com.admin.repository.TeacherRepository;

@Service
public class TeacherService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TeacherService.class);

	@Value("${kafka.producer.url}")
	private String kafkaProducerUrl;

	@Value("${kafka.topic.teacher}")
	private String kafkaTeacherTopic;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private TeacherRepository teacherRepository;

	public List<TeacherModel> getAllTeachers() {
		List<Teacher> teacherList = new ArrayList<>();
		List<TeacherModel> teacherModels = new ArrayList<>();
		try {
			teacherRepository.findAll().forEach(teacherList::add);
			for (Teacher teacher : teacherList) {
				teacherModels.add(new TeacherModel(
						teacher.getId(), 
						teacher.getName(), 
						teacher.getCity(),
						teacher.getPinCode(), 
						teacher.getDesignation(), 
						teacher.getEmail(), 
						null)
					);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		return teacherModels;
	}

	public TeacherModel getTeacher(Integer id) {
		TeacherModel teacherModel = null;
		try {
			Teacher teacher = teacherRepository.findOne(id);
			if (teacher != null) {
				teacherModel = new TeacherModel(
						teacher.getId(), 
						teacher.getName(), 
						teacher.getCity(),
						teacher.getPinCode(), 
						teacher.getDesignation(), 
						teacher.getEmail(), 
						null
					);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		return teacherModel;
	}

	public String addTeacher(TeacherModel teacherModel) {
		String url = kafkaProducerUrl + kafkaTeacherTopic;
		String responseString = null;
		try {
			if (teacherModel == null) {
				responseString = "Name, City, Pincode, Designation and Email fields are required";
			} else if (StringUtils.isBlank(teacherModel.getName())) {
				responseString = "Name field is required";
			} else if (StringUtils.isBlank(teacherModel.getCity())) {
				responseString = "City field is required";
			} else if (StringUtils.isBlank(teacherModel.getPinCode())) {
				responseString = "Pin code field is required";
			} else if (StringUtils.isBlank(teacherModel.getDesignation())) {
				responseString = "Designation field is required";
			} else if (StringUtils.isBlank(teacherModel.getEmail())) {
				responseString = "Email field is required";
			} else {
				teacherModel.setAction(CommonConstants.ADD_ACTION);
				responseString = restTemplate.postForObject(url, teacherModel, String.class);
			}
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}

	public String updateTeacher(Integer id, TeacherModel teacherModel) {
		String url = kafkaProducerUrl + kafkaTeacherTopic;
		String responseString = null;
		try {
			if (teacherModel == null) {
				responseString = "At lease one of the fields (Name, City, Pincode, Designation, Email) is required";
			} else if (StringUtils.isBlank(teacherModel.getName()) 
					&& StringUtils.isBlank(teacherModel.getCity())
					&& StringUtils.isBlank(teacherModel.getPinCode())
					&& StringUtils.isBlank(teacherModel.getDesignation())
					&& StringUtils.isBlank(teacherModel.getEmail())) {
				responseString = "At lease one of the fields (Name, City, Pincode, Designation, Email) is required";
			} else {
				teacherModel.setId(id);
				teacherModel.setAction(CommonConstants.UPDATE_ACTION);
				responseString = restTemplate.postForObject(url, teacherModel, String.class);
			}
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}

	public String deleteTeacher(Integer id) {
		String url = kafkaProducerUrl + kafkaTeacherTopic;
		String responseString = null;
		TeacherModel teacherModel = new TeacherModel();
		try {
			teacherModel.setId(id);
			teacherModel.setAction(CommonConstants.DELETE_ACTION);
			responseString = restTemplate.postForObject(url, teacherModel, String.class);
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}
}
