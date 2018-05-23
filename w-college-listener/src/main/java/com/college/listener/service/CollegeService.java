package com.college.listener.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.college.listener.entity.College;
import com.college.listener.model.CollegeModel;
import com.college.listener.repository.CollegeRepository;

@Service
public class CollegeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CollegeService.class);
	
	@Autowired
	private CollegeRepository collegeRepository;
	
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
	
	@SuppressWarnings("null")
	public CollegeModel addCollege(CollegeModel collegeModel) {
		College college = null;
		try {
			if (collegeModel != null) {
				college = new College(
						collegeModel.getName(), 
						collegeModel.getCity(), 
						collegeModel.getPinCode(), 
						collegeModel.getMinStandard(),
						collegeModel.getMaxStandard(), 
						collegeModel.getEmail()
					);
				
				collegeRepository.save(college);
				collegeModel.setId(college.getId());
				
			} else {
				collegeModel.setId(null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return collegeModel;
	}
	
	@SuppressWarnings("null")
	public CollegeModel updateCollege(Integer id, CollegeModel collegeModel) {
		College college = null;
		try {
			college = collegeRepository.findOne(id);
			if (collegeModel != null) {
				if (StringUtils.isNotBlank(collegeModel.getName())) {
					college.setName(collegeModel.getName());
				}
				if (StringUtils.isNotBlank(collegeModel.getCity())) {
					college.setCity(collegeModel.getCity());
				}
				if (StringUtils.isNotBlank(collegeModel.getPinCode())) {
					college.setPinCode(collegeModel.getPinCode());
				}
				if (StringUtils.isNotBlank(collegeModel.getMinStandard())) {
					college.setMinStandard(collegeModel.getMinStandard());
				}
				if (StringUtils.isNotBlank(collegeModel.getMaxStandard())) {
					college.setMaxStandard(collegeModel.getMaxStandard());
				}
				if (StringUtils.isNotBlank(collegeModel.getEmail())) {
					college.setEmail(collegeModel.getEmail());
				}
				
				collegeRepository.save(college);
				collegeModel.setId(college.getId());
				
			} else {
				collegeModel.setId(null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		
		return collegeModel;
	}
	
	public Integer deleteCollege(Integer id) {
		try {
			collegeRepository.delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		return id;
	}
}
