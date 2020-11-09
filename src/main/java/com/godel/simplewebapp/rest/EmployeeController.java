package com.godel.simplewebapp.rest;

import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employee")
@Api(tags = "Employee ", description = "Employee Management System ")
@ApiResponses({
        @ApiResponse(code = 200, message = "Success "),
        @ApiResponse(code = 201, message = "Created "),
        @ApiResponse(code = 204, message = "No content "),
        @ApiResponse(code = 400, message = "Bad request "),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found ")
})
public class EmployeeController {

    private final EmployeeService employeeService;

    private static final Logger LOGGER = LogManager.getLogger(EmployeeController.class);

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation("View a list of employees ")
    @GetMapping
    public List<Employee> getAll(){
        LOGGER.debug("Get all employees");

        return employeeService.getAll();
    }

    @ApiOperation("Get an employee by id")
    @GetMapping("/{id}")
    public Employee getById(@PathVariable("id") Integer id){
        LOGGER.debug("Get employee with id = {}", id);

        return employeeService.getById(id);
    }

    @ApiOperation("Add an employee ")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Employee addEmployee(@Valid @RequestBody Employee employee){
        LOGGER.debug("Add employee {}", employee);

        return employeeService.add(employee);
    }

    @ApiOperation("Update an employee by id")
    @PutMapping("/{id}")
    public Employee update(@PathVariable("id") Integer id,@Valid @RequestBody Employee employee){
        LOGGER.debug("Update employee with id {} {}", id, employee);

        return employeeService.update(id, employee);
    }

    @ApiOperation("Delete an employee by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        LOGGER.debug("Delete employee with id {}", id);

        employeeService.deleteById(id);
    }

}
