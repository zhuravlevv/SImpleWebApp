package com.godel.simplewebapp.dao.impl;

import com.godel.simplewebapp.dao.EmployeeDao;
import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.dto.Gender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class EmployeeDaoImplTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void shouldReturnAllEmployees(){
        List<Employee> employees = employeeDao.getAll();

        Assert.assertEquals(1, employees.size());
    }

    @Test
    public void shouldReturnEmployeeById(){
        Employee employee = employeeDao.getById(1);

        Assert.assertEquals("first_name", employee.getFirstName());
    }

    @Test
    public void shouldAddEmployee(){
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new Date());
        Employee addedEmployee = employeeDao.add(employee);
        List<Employee> employees = employeeDao.getAll();

        Assert.assertEquals(2, employees.size());
        Assert.assertEquals(employee, addedEmployee);
    }

    @Test
    public void shouldDeleteEmployeeById(){
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new Date());
        Employee addedEmployee = employeeDao.add(employee);
        List<Employee> employees = employeeDao.getAll();
        employeeDao.deleteById(addedEmployee.getEmployeeId());
        List<Employee> currentEmployees = employeeDao.getAll();

        Assert.assertEquals(employees.size() - 1, currentEmployees.size());
    }
    @Test
    public void shouldUpdateEmployee(){
        Employee employee = employeeDao.getById(1);
        employee.setLastName("new_last_name");
        Employee updatedEmployee = employeeDao.update(employee);
        Assert.assertEquals("new_last_name", updatedEmployee.getLastName());
    }
}
