package com.godel.simplewebapp.dao;

import com.godel.simplewebapp.dto.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer> {
}
