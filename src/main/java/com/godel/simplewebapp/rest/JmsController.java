package com.godel.simplewebapp.rest;

import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.service.jms.EmployeeProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/async/employees")
public class JmsController {

    private final EmployeeProducerService employeeJmsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsController.class);

    public JmsController(EmployeeProducerService employeeJmsService) {
        this.employeeJmsService = employeeJmsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee sendEmployee(@RequestBody Employee employee){
        LOGGER.debug("sendEmployee({})", employee);

        employeeJmsService.addEmployeeAsync(employee);
        return employee;
    }
}
