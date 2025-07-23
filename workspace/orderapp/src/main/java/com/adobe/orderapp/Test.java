package com.adobe.orderapp;

import com.adobe.orderapp.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = new Employee();
        employee.setId(123);
        employee.setTitle("Sr. Programmer");
        var personal = new HashMap<String, String>();
        personal.put("firstName", "Harry");
        personal.put("lastName", "Potter");
        personal.put("phone", "1234567890");

        var skills = new ArrayList<String>();

        skills.add("Spring Boot");
        skills.add("JPA");

        employee.setProgrammingSkills(skills);
        employee.setPersonalInfo(personal);

        System.out.println(mapper.writeValueAsString(employee));
    }
}
