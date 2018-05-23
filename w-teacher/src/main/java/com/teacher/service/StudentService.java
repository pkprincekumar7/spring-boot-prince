package com.teacher.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.teacher.constants.CommonConstants;
import com.teacher.entity.Student;
import com.teacher.model.StudentModel;
import com.teacher.repository.StudentRepository;

@Service
public class StudentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

	@Value("${kafka.producer.url}")
	private String kafkaProducerUrl;

	@Value("${kafka.topic.student}")
	private String kafkaStudentTopic;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private StudentRepository studentRepository;

	public StudentModel getStudent(Integer id) {
		StudentModel studentModel = null;
		try {
			Student student = studentRepository.findOne(id);
			if (student != null) {
				studentModel = new StudentModel(
						student.getId(), 
						student.getName(), 
						student.getCity(),
						student.getPinCode(), 
						student.getStandard(), 
						student.getPercentageMarks(), 
						student.getEmail(),
						null
					);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		return studentModel;
	}

	public String updateStudent(Integer id, StudentModel studentModel) {
		String url = kafkaProducerUrl + kafkaStudentTopic;
		String responseString = null;
		try {
			if (studentModel == null || studentModel.getPercentageMarks() == null) {
				responseString = "Percentage Marks is required";
			} else if (StringUtils.isNotBlank(studentModel.getName())
					|| StringUtils.isNotBlank(studentModel.getCity())
					|| StringUtils.isNotBlank(studentModel.getPinCode())
					|| StringUtils.isNotBlank(studentModel.getStandard())
					|| StringUtils.isNotBlank(studentModel.getEmail())) {
				responseString = "Teacher can update only Percentage Marks";
			} else if (studentModel.getPercentageMarks() < 0.0) {
				responseString = "Percentage Marks should not be less than 0.0";
			} else {
				studentModel.setId(id);
				studentModel.setAction(CommonConstants.UPDATE_ACTION);
				responseString = restTemplate.postForObject(url, studentModel, String.class);
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
