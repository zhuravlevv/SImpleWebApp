package com.godel.simplewebapp.service.impl;

import com.godel.simplewebapp.dao.EmployeeDao;
import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.service.EmployeeService;
import com.godel.simplewebapp.service.exceptions.DateOfBirthException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

    @Override
    public Employee getById(Integer id) {
        return employeeDao.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        employeeDao.deleteById(id);
    }

    @Override
    public Employee add(Employee employee) {
        Date dateOfBirth = employee.getDateOfBirth();
        Date currentDate = new Date();
        if(dateOfBirth.compareTo(currentDate) >= 0){
            throw new DateOfBirthException(dateOfBirth.toString());
        }
        return employeeDao.add(employee);
    }

    @Override
    public Employee update(Integer id, Employee employee) {
        Date dateOfBirth = employee.getDateOfBirth();
        Date currentDate = new Date();
        if(dateOfBirth.compareTo(currentDate) >= 0){
            throw new DateOfBirthException(dateOfBirth.toString());
        }
        Employee currentEmployee = employeeDao.getById(id);
        currentEmployee.setFirstName(employee.getFirstName());
        currentEmployee.setLastName(employee.getLastName());
        currentEmployee.setDepartmentId(employee.getDepartmentId());
        currentEmployee.setJobTitle(employee.getJobTitle());
        currentEmployee.setDateOfBirth(employee.getDateOfBirth());
        return employeeDao.update(currentEmployee);
    }

}
