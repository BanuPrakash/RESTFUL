package com.adobe.demo.service;

import com.adobe.demo.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class AppService {
    @Autowired
    private EmployeeRepo employeeRepo;

    // connection pool was created using factory method
    @Autowired
    private DataSource dataSource;

    public void doTask(){
        try {
            System.out.println(dataSource.getConnection()); // pick connection from pool
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        employeeRepo.addEmployee();
    }
}
