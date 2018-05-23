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
import com.admin.entity.Student;
import com.admin.model.StudentModel;
import com.admin.repository.StudentRepository;

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

	public List<StudentModel> getAllStudents() {
		List<Student> studentList = new ArrayList<>();
		List<StudentModel> studentModels = new ArrayList<>();
		try {
			studentRepository.findAll().forEach(studentList::add);
			for (Student student : studentList) {
				studentModels.add(new StudentModel(
						student.getId(), 
						student.getName(), 
						student.getCity(), 
						student.getPinCode(),
						student.getStandard(), 
						student.getPercentageMarks(), 
						student.getEmail(), 
						null)
					);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		return studentModels;
	}

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

	public String addStudent(StudentModel studentModel) {
		String url = kafkaProducerUrl + kafkaStudentTopic;
		String responseString = null;
		try {
			if (studentModel == null) {
				responseString = "Name, City, Pincode, Standard, Percentage Marks and Email fields are required";
			} else if (StringUtils.isBlank(studentModel.getName())) {
				responseString = "Name field is required";
			} else if (StringUtils.isBlank(studentModel.getCity())) {
				responseString = "City field is required";
			} else if (StringUtils.isBlank(studentModel.getPinCode())) {
				responseString = "Pin code field is required";
			} else if (StringUtils.isBlank(studentModel.getStandard())) {
				responseString = "Standard field is required";
			} else if (studentModel.getPercentageMarks() == null) {
				responseString = "Percentage Marks field is required";
			} else if (studentModel.getPercentageMarks() < 0.0) {
				responseString = "Percentage Marks should not be less than 0.0";
			} else if (StringUtils.isBlank(studentModel.getEmail())) {
				responseString = "Email field is required";
			} else {
				studentModel.setAction(CommonConstants.ADD_ACTION);
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

	public String updateStudent(Integer id, StudentModel studentModel) {
		String url = kafkaProducerUrl + kafkaStudentTopic;
		String responseString = null;
		try {
			if (studentModel == null) {
				responseString = "At lease one of the fields (Name, City, Pincode, Standard, Percentage Marks, Email) is required";
			} else if (StringUtils.isBlank(studentModel.getName())
					&& StringUtils.isBlank(studentModel.getCity())
					&& StringUtils.isBlank(studentModel.getPinCode())
					&& StringUtils.isBlank(studentModel.getStandard())
					&& (studentModel.getPercentageMarks() == null)
					&& StringUtils.isBlank(studentModel.getEmail())) {
				responseString = "At lease one of the fields (Name, City, Pincode, Standard, Percentage Marks, Email) is required";
			} else if (studentModel.getPercentageMarks() != null && studentModel.getPercentageMarks() < 0.0) {
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

	public String deleteStudent(Integer id) {
		String url = kafkaProducerUrl + kafkaStudentTopic;
		String responseString = null;
		StudentModel studentModel = new StudentModel();
		try {
			studentModel.setId(id);
			studentModel.setAction(CommonConstants.DELETE_ACTION);
			responseString = restTemplate.postForObject(url, studentModel, String.class);
		} catch (Exception e) {
			responseString = CommonConstants.SERVER_ERROR;
			LOGGER.error(e.getMessage());
			throw e;
		}
		LOGGER.info(responseString);
		return responseString;
	}
}
