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
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("employee")
@Api(tags = "Employee ", description = "Employee Management System ")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success "),
        @ApiResponse(code = 400, message = "Bad request "),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found ")
})
public class EmployeeController {

    @NonNull
    private final EmployeeService employeeService;

    private static final Logger LOGGER = LogManager.getLogger(EmployeeController.class);

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiResponse(code = 200, message = "Success ",
            response = Employee.class, responseContainer = "List ")
    @ApiOperation(value = "View a list of employees ")
    @GetMapping
    public ResponseEntity<List<Employee>> getAll(){
        LOGGER.debug("Get all employees");
        List<Employee> employees = employeeService.getAll();
        if(employees.isEmpty()){
            return ResponseEntity.notFound()
                    .build();
        }
        return ResponseEntity.ok(employees);
    }

    @ApiOperation(value = "Get an employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable("id") Integer id){
        LOGGER.debug("Get employee with id = {}", id);
        if(id == null){
            return ResponseEntity.notFound()
                    .build();
        }
        Employee employee = employeeService.getById(id);
        if(employee == null){
            return ResponseEntity.notFound()
                    .build();
        }
        return ResponseEntity.ok(employee);
    }

    @ApiOperation(value = "Add an employee ")
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee){
        LOGGER.debug("Add employee {}", employee);
        if(employee == null){
            return ResponseEntity.badRequest()
                    .body(null);
        }
        Employee createdEmployee = employeeService.add(employee);
        return ResponseEntity.created(URI.create("employee/" + createdEmployee.getEmployeeId()))
                .body(createdEmployee);
    }

    @ApiOperation(value = "Update an employee by id")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") Integer id,@Valid @RequestBody Employee employee){
        LOGGER.debug("Update employee with id {} {}", id, employee);
        if(id == null){
            return ResponseEntity.notFound()
                    .build();
        }
        if(employee == null){
            return ResponseEntity.badRequest()
                    .body(null);
        }
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    @ApiOperation(value = "Delete an employee by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        LOGGER.debug("Delete employee with id {}", id);
        if(id == null){
            return ResponseEntity.notFound()
                    .build();
        }
        employeeService.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }

}
