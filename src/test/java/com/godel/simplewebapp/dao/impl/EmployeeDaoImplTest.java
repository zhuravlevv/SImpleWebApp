package com.godel.simplewebapp.dao.impl;

import com.godel.simplewebapp.dao.EmployeeDao;
import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.dto.Gender;
import com.godel.simplewebapp.exceptions.EmployeeServiceException;
import com.godel.simplewebapp.exceptions.NotFoundEmployeeServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeDaoImplTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void shouldReturnAllEmployees(){
        List<Employee> employees = StreamSupport
                .stream(employeeDao.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Assert.assertNotNull(employees);
    }

    @Test
    public void shouldReturnEmployeeById(){
        Employee employee = employeeDao.findById(1)
                .orElseThrow( () ->
                        new EmployeeServiceException(
                                new NotFoundEmployeeServiceException("Employee with id 1 not found")));

        Assert.assertEquals("first_name", employee.getFirstName());
    }

    @Test
    public void shouldAddEmployee(){
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new GregorianCalendar());
        Employee addedEmployee = employeeDao.save(employee);
        List<Employee> employees = StreamSupport
                .stream(employeeDao.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Assert.assertEquals(1, employees.size());
        Assert.assertEquals(employee, addedEmployee);
    }

    @Test
    public void shouldDeleteEmployeeById() {
        Calendar calendar = new GregorianCalendar(2020, Calendar.FEBRUARY, 1);
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, calendar);
        Employee addedEmployee = employeeDao.save(employee);
        List<Employee> employees = StreamSupport
                .stream(employeeDao.findAll().spliterator(), false)
                .collect(Collectors.toList());

        employeeDao.deleteById(addedEmployee.getEmployeeId());

        List<Employee> currentEmployees = StreamSupport
                .stream(employeeDao.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Assert.assertEquals(employees.size() - 1, currentEmployees.size());
    }
    @Test
    public void shouldUpdateEmployee(){
        Employee employee = employeeDao.findById(1)
                .orElseThrow( () ->
                    new EmployeeServiceException(
                        new NotFoundEmployeeServiceException("Employee with id 1 not found")));
        employee.setLastName("new_last_name");
        Employee updatedEmployee = employeeDao.save(employee);
        Assert.assertEquals("new_last_name", updatedEmployee.getLastName());
    }
}
