package com.teacher.listener.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teacher.listener.entity.Teacher;
import com.teacher.listener.model.TeacherModel;
import com.teacher.listener.repository.TeacherRepository;

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
	
	@SuppressWarnings("null")
	public TeacherModel addTeacher(TeacherModel teacherModel) {
		Teacher teacher = null;
		try {
			if (teacherModel != null) {
				teacher = new Teacher(
						teacherModel.getName(), 
						teacherModel.getCity(), 
						teacherModel.getPinCode(), 
						teacherModel.getDesignation(), 
						teacherModel.getEmail()
					);
				
				teacherRepository.save(teacher);
				teacherModel.setId(teacher.getId());
				
			} else {
				teacherModel.setId(null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return teacherModel;
	}
	
	@SuppressWarnings("null")
	public TeacherModel updateTeacher(Integer id, TeacherModel teacherModel) {
		Teacher teacher = null;
		try {
			teacher = teacherRepository.findOne(id);
			if (teacherModel != null) {
				if (StringUtils.isNotBlank(teacherModel.getName())) {
					teacher.setName(teacherModel.getName());
				}
				if (StringUtils.isNotBlank(teacherModel.getCity())) {
					teacher.setCity(teacherModel.getCity());
				}
				if (StringUtils.isNotBlank(teacherModel.getPinCode())) {
					teacher.setPinCode(teacherModel.getPinCode());
				}
				if (StringUtils.isNotBlank(teacherModel.getDesignation())) {
					teacher.setDesignation(teacherModel.getDesignation());
				}
				if (StringUtils.isNotBlank(teacherModel.getEmail())) {
					teacher.setEmail(teacherModel.getEmail());
				}
				
				teacherRepository.save(teacher);
				teacherModel.setId(teacher.getId());
				
			} else {
				teacherModel.setId(null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return teacherModel;
	}
	
	public Integer deleteTeacher(Integer id) {
		try {
			teacherRepository.delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		return id;
	}
}
