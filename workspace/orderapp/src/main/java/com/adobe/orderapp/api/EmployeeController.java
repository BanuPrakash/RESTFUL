package com.adobe.orderapp.api;

import com.adobe.orderapp.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    Employee employee = new Employee();

    public EmployeeController() {
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
    }
    /*
        {
            "id": 123,
            "title": "Team Lead",
            "personalInfo" : {
                "firstName" : "Harry",
                "lastName": "Potter",
                "email" : "harry@abc.com"
            },
            programmingSkills: ["Spring Boot", "AWS", "JPA"]
        }
     */
    @PatchMapping("/{id}")
    public  Employee patchMapping(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        var target = patch.apply(mapper.readTree(mapper.writeValueAsString(employee)));
        return mapper.treeToValue(target, Employee.class); // JSONNode to Employee
    }
}
