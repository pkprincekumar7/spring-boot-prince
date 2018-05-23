package com.admin.listener.repository;

import org.springframework.data.repository.CrudRepository;

import com.admin.listener.entity.Admin;

public interface AdminRepository extends CrudRepository<Admin, Integer> {

}
