package com.godel.simplewebapp.rest;

import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employee")
@Api(tags = "Employee ", description = "Employee Management System ")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success "),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found ")
})
public class EmployeeController {

    @NonNull
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiResponse(code = 200, message = "Success ",
            response = Employee.class, responseContainer = "List ")
    @ApiOperation(value = "View a list of employees ")
    @GetMapping
    public ResponseEntity<List<Employee>> getAll(){
        return ResponseEntity.ok(employeeService.getAll());
    }

    @ApiOperation(value = "Get an employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @ApiOperation(value = "Add an employee ")
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.add(employee));
    }

    @ApiOperation(value = "Update an employee by id")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id") Integer id, @RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    @ApiOperation(value = "Delete an employee by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
