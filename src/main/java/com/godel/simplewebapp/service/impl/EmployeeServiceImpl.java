package com.godel.simplewebapp.service.impl;

import com.godel.simplewebapp.dao.EmployeeDao;
import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.exceptions.EmployeeServiceException;
import com.godel.simplewebapp.exceptions.NotFoundEmployeeServiceException;
import com.godel.simplewebapp.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> getAll() {
        LOGGER.debug("Get all employees");
        try {
            List<Employee> employees = StreamSupport
                    .stream(employeeDao.findAll().spliterator(), false)
                    .collect(Collectors.toList());
            if(employees.isEmpty()){
                throw new NotFoundEmployeeServiceException("Employees not found");
            }
            return employees;
        }
        catch (DataAccessException e){
            throw new EmployeeServiceException("Couldn't get employees", e);
        }
    }

    @Override
    public Employee getById(Integer id) {
        LOGGER.debug("Get employee with id = {}", id);
        try {
            return employeeDao.findById(id)
                    .orElseThrow(() ->
                            new NotFoundEmployeeServiceException("Employee with id " + id + " not found"));
        }
        catch (DataAccessException e){
            throw new EmployeeServiceException("Couldn't get employee by id", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        LOGGER.debug("Delete employee with id {}", id);

        try {
            employeeDao.deleteById(id);
        }
        catch (DataAccessException e){
            throw new EmployeeServiceException("Couldn't delete employee by id", e);
        }

    }

    @Override
    public Employee add(Employee employee) {
        LOGGER.debug("Add employee {}", employee);
        employee.setEmployeeId(null);
        try {
            return employeeDao.save(employee);
        }
        catch (DataAccessException e){
            throw new EmployeeServiceException("Employee saving went wrong", e);
        }
    }

    @Override
    public Employee update(Integer id, Employee employee) {
        LOGGER.debug("Update employee with id {} {}", id, employee);
        try {
            Employee currentEmployee = employeeDao.findById(id)
                    .orElseThrow(() ->
                            new NotFoundEmployeeServiceException("Employee with id " + id + " not found"));
            currentEmployee.setFirstName(employee.getFirstName());
            currentEmployee.setLastName(employee.getLastName());
            currentEmployee.setDepartmentId(employee.getDepartmentId());
            currentEmployee.setJobTitle(employee.getJobTitle());
            currentEmployee.setDateOfBirth(employee.getDateOfBirth());
            return employeeDao.save(currentEmployee);
        }
        catch (DataAccessException e){
            throw new EmployeeServiceException("Employee updating went wrong", e);
        }
    }

}
