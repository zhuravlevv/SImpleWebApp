package com.godel.simplewebapp.rest;

import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.service.jms.EmployeeProducerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/async/employees")
@Api(tags = "Jms ", description = "Jms controller ")
@ApiResponses({
        @ApiResponse(code = 500, message = "Internal Server Error ")
})
public class JmsController {

    private final EmployeeProducerService employeeJmsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsController.class);

    public JmsController(EmployeeProducerService employeeJmsService) {
        this.employeeJmsService = employeeJmsService;
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok "),
            @ApiResponse(code = 400, message = "Bad request ")
    })
    public Employee sendEmployee(@Valid @RequestBody Employee employee){
        LOGGER.debug("sendEmployee({})", employee);

        employeeJmsService.addEmployeeAsync(employee);
        return employee;
    }
}
