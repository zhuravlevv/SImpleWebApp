package com.godel.simplewebapp.service.impl;

import com.godel.simplewebapp.dao.EmployeeDao;
import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.dto.Gender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeDao employeeDao;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAllEmployees(){
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new GregorianCalendar());
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        when(employeeDao.findAll()).thenReturn(employees);
        List<Employee> getAllEmployees = employeeService.getAll();

        Assert.assertEquals(1, getAllEmployees.size());
    }

    @Test
    public void shouldReturnEmployeeById(){
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new GregorianCalendar());

        when(employeeDao.findById(1)).thenReturn(Optional.of(employee));
        Employee returnedEmployee = employeeService.getById(1);

        Assert.assertEquals(employee, returnedEmployee);
    }

    @Test
    public void shouldAddEmployee() {
        Employee employee = new Employee();
        employee.setDateOfBirth(new GregorianCalendar());
        when(employeeDao.save(any(Employee.class))).then(returnsFirstArg());
        Employee addedEmployee = employeeService.add(employee);

        Assert.assertEquals(employee, addedEmployee);
    }

    @Test
    public void shouldUpdateEmployee(){
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new GregorianCalendar());
        Employee oldEmployee = new Employee();

        when(employeeDao.findById(1)).thenReturn(Optional.of(oldEmployee));
        when(employeeDao.save(any(Employee.class))).then(returnsFirstArg());
        Employee addedEmployee = employeeService.update(1, employee);

        Assert.assertEquals(oldEmployee, addedEmployee);
    }

}