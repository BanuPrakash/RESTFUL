package com.adobe.demo.repo;

import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoDbImpl implements EmployeeRepo{
    @Override
    public void addEmployee() {
        System.out.println("Stored in database!!!");
    }
}
