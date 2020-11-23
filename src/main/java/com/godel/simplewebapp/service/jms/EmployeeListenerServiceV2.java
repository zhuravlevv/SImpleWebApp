package com.godel.simplewebapp.service.jms;

import com.godel.simplewebapp.dto.Employee;
import com.godel.simplewebapp.service.EmployeeService;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

//@Component
public class EmployeeListenerServiceV2 implements MessageListener {

    private final EmployeeService employeeService;

    public EmployeeListenerServiceV2(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void onMessage(Message message) {
        if(message instanceof Employee){
            Employee employee = (Employee) message;
            employeeService.add(employee);
        }
        else{
            System.out.println("Wrong type");
        }
    }
}
