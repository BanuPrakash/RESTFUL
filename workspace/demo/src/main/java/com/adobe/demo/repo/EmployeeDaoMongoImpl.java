package com.adobe.demo.repo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("prod")
@Repository
public class EmployeeDaoMongoImpl implements EmployeeRepo{
    @Override
    public void addEmployee() {
        System.out.println("Mongo Store!!!");
    }
}
