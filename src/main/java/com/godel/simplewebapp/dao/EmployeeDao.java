package com.godel.simplewebapp.dao;

import com.godel.simplewebapp.dto.Employee;

import java.util.List;

public interface EmployeeDao{
    List<Employee> getAll();

    Employee getById(Integer id);

    void deleteById(Integer id);

    Employee add(Employee employee);

    Employee update(Employee employee);
}
