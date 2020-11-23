package com.godel.simplewebapp.service.jms;

import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.rest.JmsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProducerService {

    private final JmsTemplate jmsTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsController.class);

    @Value("${employee.destination}")
    private String destination;

    public EmployeeProducerService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void addEmployeeAsync(Employee employee){
        LOGGER.debug("addEmployeeAsync({})", employee);

        jmsTemplate.convertAndSend(destination, employee);
    }
}
