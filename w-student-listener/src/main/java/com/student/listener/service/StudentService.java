package com.student.listener.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.listener.entity.Student;
import com.student.listener.model.StudentModel;
import com.student.listener.repository.StudentRepository;

@Service
public class StudentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
	
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
	
	@SuppressWarnings("null")
	public StudentModel addStudent(StudentModel studentModel) {
		Student student = null;
		try {
			if (studentModel != null) {
				student = new Student(
						studentModel.getName(), 
						studentModel.getCity(), 
						studentModel.getPinCode(), 
						studentModel.getStandard(), 
						studentModel.getPercentageMarks(), 
						studentModel.getEmail()
					);
				
				studentRepository.save(student);
				studentModel.setId(student.getId());
				
			} else {
				studentModel.setId(null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return studentModel;
	}
	
	@SuppressWarnings("null")
	public StudentModel updateStudent(Integer id, StudentModel studentModel) {
		Student student = null;
		try {
			student = studentRepository.findOne(id);
			if (studentModel != null) {
				if (StringUtils.isNotBlank(studentModel.getName())) {
					student.setName(studentModel.getName());
				}
				if (StringUtils.isNotBlank(studentModel.getCity())) {
					student.setCity(studentModel.getCity());
				}
				if (StringUtils.isNotBlank(studentModel.getPinCode())) {
					student.setPinCode(studentModel.getPinCode());
				}
				if (StringUtils.isNotBlank(studentModel.getStandard())) {
					student.setStandard(studentModel.getStandard());
				}
				if (studentModel.getPercentageMarks() != null || studentModel.getPercentageMarks() >= 0.0) {
					student.setPercentageMarks(studentModel.getPercentageMarks());
				}
				if (StringUtils.isNotBlank(studentModel.getEmail())) {
					student.setEmail(studentModel.getEmail());
				}
				
				studentRepository.save(student);
				studentModel.setId(student.getId());
				
			} else {
				studentModel.setId(null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return studentModel;
	}
	
	public Integer deleteStudent(Integer id) {
		try {
			studentRepository.delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		return id;
	}
}
