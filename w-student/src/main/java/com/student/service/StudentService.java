package com.student.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.entity.Student;
import com.student.model.StudentModel;
import com.student.repository.StudentRepository;

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
}
