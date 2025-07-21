package com.adobe.demo.service;

import com.adobe.demo.repo.EmployeeRepo;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class AppService  implements BeanNameAware {

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

    @Override
    public void setBeanName(String name) {
        System.out.println("Name set to " + name);
    }
}
