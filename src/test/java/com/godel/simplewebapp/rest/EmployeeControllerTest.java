package com.godel.simplewebapp.rest;

import com.godel.simplewebapp.Application;
import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.dto.Gender;
import com.godel.simplewebapp.rest.EmployeeController;
import com.godel.simplewebapp.service.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public EmployeeService employeeService;

    @Test
    public void getAll() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new Date());
        employee.setEmployeeId(1);
        employee.setDateOfBirth(null);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        when(employeeService.getAll()).thenReturn(employees);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/employee").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        String expected = "[{employeeId:1,firstName:first_name,lastName:last_name,departmentId:1,jobTitle:job_title,gender:MALE,dateOfBirth:null}]";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void getById() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new Date());
        employee.setEmployeeId(1);
        employee.setDateOfBirth(null);

        when(employeeService.getById(1)).thenReturn(employee);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/employee/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{employeeId:1,firstName:first_name,lastName:last_name,departmentId:1,jobTitle:job_title,gender:MALE,dateOfBirth:null}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void update() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new Date(1577836800000L));
        employee.setEmployeeId(1);

        String employeeJson = "{\"employeeId\":\"1\",\"firstName\":\"first_name\",\"lastName\":\"last_name\",\"departmentId\":\"1\",\"jobTitle\":\"job_title\",\"gender\":\"MALE\",\"dateOfBirth\":\"2020-01-01\"}";

        when(employeeService.update(any(Integer.class), any(Employee.class))).thenReturn(employee);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/employee/1")
                .accept(MediaType.APPLICATION_JSON).content(employeeJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());

        String expected = "{employeeId:1,firstName:first_name,lastName:last_name,departmentId:1,jobTitle:job_title,gender:MALE,dateOfBirth:\"2020-01-01T00:00:00.000+0000\"}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void add() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, new Date(1577836800000L));
        employee.setEmployeeId(1);

        String employeeJson = "{\"employeeId\":\"1\",\"firstName\":\"first_name\",\"lastName\":\"last_name\",\"departmentId\":\"1\",\"jobTitle\":\"job_title\",\"gender\":\"MALE\",\"dateOfBirth\":\"2020-01-01\"}";

        when(employeeService.add(any(Employee.class))).thenReturn(employee);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/employee")
                .accept(MediaType.APPLICATION_JSON).content(employeeJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());

        String expected = "{employeeId:1,firstName:first_name,lastName:last_name,departmentId:1,jobTitle:job_title,gender:MALE,dateOfBirth:\"2020-01-01T00:00:00.000+0000\"}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

}
