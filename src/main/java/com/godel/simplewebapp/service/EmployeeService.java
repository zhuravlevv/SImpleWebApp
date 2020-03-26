package com.godel.simplewebapp.service;

import com.godel.simplewebapp.dto.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAll();

    public Employee getById(Integer id);

    public void deleteById(Integer id);

    public Employee add(Employee employee);

    public Employee update(Integer id, Employee employee);
}
