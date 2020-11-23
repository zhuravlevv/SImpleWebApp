package com.godel.simplewebapp.service.jms;

import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeListenerService {

    private final EmployeeService employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeListenerService.class);

    public EmployeeListenerService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @JmsListener(destination = "#{'${employee.destination}'}")
    public void onMessage(Employee employee) {
        LOGGER.debug("onMessage({})", employee);

        employeeService.add(employee);
    }
}
