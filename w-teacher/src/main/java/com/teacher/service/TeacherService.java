package com.teacher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teacher.entity.Teacher;
import com.teacher.model.TeacherModel;
import com.teacher.repository.TeacherRepository;

@Service
public class TeacherService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TeacherService.class);

	@Autowired
	private TeacherRepository teacherRepository;

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
}
