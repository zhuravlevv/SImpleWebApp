package com.godel.simplewebapp.service.impl;

import com.godel.simplewebapp.dao.EmployeeDao;
import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.dto.Gender;
import com.godel.simplewebapp.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeDao employeeDao;

    @Test
    public void shouldReturnAllEmployees(){
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new Date());
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        when(employeeDao.getAll()).thenReturn(employees);
        List<Employee> getAllEmployees = employeeService.getAll();

        Assert.assertEquals(1, getAllEmployees.size());
    }

    @Test
    public void shouldReturnEmployeeById(){
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new Date());

        when(employeeDao.getById(1)).thenReturn(employee);
        Employee returnedEmployee = employeeService.getById(1);

        Assert.assertEquals(employee, returnedEmployee);
    }

    @Test
    public void shouldAddEmployee() {
        Employee employee = new Employee();

        when(employeeDao.add(any(Employee.class))).then(returnsFirstArg());
        Employee addedEmployee = employeeService.add(employee);

        Assert.assertEquals(employee, addedEmployee);
    }

    @Test
    public void shouldUpdateEmployee(){
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new Date());
        Employee oldEmployee = new Employee();

        when(employeeDao.getById(1)).thenReturn(oldEmployee);
        when(employeeDao.update(any(Employee.class))).then(returnsFirstArg());
        Employee addedEmployee = employeeService.update(1, employee);

        Assert.assertEquals(oldEmployee, addedEmployee);
    }

}