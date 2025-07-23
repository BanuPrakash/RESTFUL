package com.adobe.orderapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Employee {
    int id;
    String title;
    Map<String, String> personalInfo = new HashMap<>();
    List<String> programmingSkills = new ArrayList<>();
}
