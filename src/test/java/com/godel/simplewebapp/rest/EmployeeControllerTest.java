package com.godel.simplewebapp.rest;

import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.dto.Gender;
import com.godel.simplewebapp.service.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    public EmployeeService employeeService;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .build();
    }

    @Test
    public void getAll() throws Exception {
        Calendar calendar = new GregorianCalendar(2020, 01, 01);
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, calendar);
        employee.setEmployeeId(1);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        when(employeeService.getAll()).thenReturn(employees);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("first_name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("last_name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].jobTitle", Matchers.is("job_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender", Matchers.is("MALE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateOfBirth", Matchers.is(1580504400000L)));

    }

    @Test
    public void getById() throws Exception {
        Calendar calendar = new GregorianCalendar(2020, 01, 01);
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, calendar);
        employee.setEmployeeId(1);
        when(employeeService.getById(anyInt())).thenReturn(employee);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("employeeId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", Matchers.is("first_name")))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", Matchers.is("last_name")))
                .andExpect(MockMvcResultMatchers.jsonPath("departmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("jobTitle", Matchers.is("job_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("gender", Matchers.is("MALE")))
                .andExpect(MockMvcResultMatchers.jsonPath("dateOfBirth", Matchers.is(1580504400000L)));
    }

    @Test
    public void update() throws Exception {
        Calendar calendar = new GregorianCalendar(2020, 01, 01);
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, calendar);
        employee.setEmployeeId(1);
        when(employeeService.update(any(Integer.class), any(Employee.class))).thenReturn(employee);

        String employeeJson = "{\"employeeId\":\"2\",\"firstName\":\"first_name\",\"lastName\":\"last_name\",\"departmentId\":\"1\",\"jobTitle\":\"job_title\",\"gender\":\"MALE\",\"dateOfBirth\":\"2020-01-01\"}";

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/employee/1")
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .characterEncoding("UTF-8")
                                        .content(employeeJson);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("employeeId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", Matchers.is("first_name")))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", Matchers.is("last_name")))
                .andExpect(MockMvcResultMatchers.jsonPath("departmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("jobTitle", Matchers.is("job_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("gender", Matchers.is("MALE")))
                .andExpect(MockMvcResultMatchers.jsonPath("dateOfBirth", Matchers.is(1580504400000L)));
    }

    @Test
    public void add() throws Exception {
        Calendar calendar = new GregorianCalendar(2020, 01, 01);
        Employee employee = new Employee("first_name", "last_name", 1, "job_title", Gender.MALE, calendar);
        employee.setEmployeeId(1);
        when(employeeService.add(any(Employee.class))).thenReturn(employee);

        String employeeJson = "{\"employeeId\":\"1\",\"firstName\":\"first_name\",\"lastName\":\"last_name\",\"departmentId\":\"1\",\"jobTitle\":\"job_title\",\"gender\":\"MALE\",\"dateOfBirth\":\"2020-01-01\"}";

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/employee")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(employeeJson);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("employeeId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName", Matchers.is("first_name")))
                .andExpect(MockMvcResultMatchers.jsonPath("lastName", Matchers.is("last_name")))
                .andExpect(MockMvcResultMatchers.jsonPath("departmentId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("jobTitle", Matchers.is("job_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("gender", Matchers.is("MALE")))
                .andExpect(MockMvcResultMatchers.jsonPath("dateOfBirth", Matchers.is(1580504400000L)));

    }

    @Test
    public void delete() throws Exception {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }


}
