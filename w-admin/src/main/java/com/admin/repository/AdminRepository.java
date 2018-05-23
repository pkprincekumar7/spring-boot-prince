package com.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.admin.entity.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer> {

}
