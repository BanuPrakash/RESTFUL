package com.adobe.demo.service;

import com.adobe.demo.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    @Autowired
    private EmployeeRepo employeeRepo;

    public void doTask() {
        employeeRepo.addEmployee();
    }
}
