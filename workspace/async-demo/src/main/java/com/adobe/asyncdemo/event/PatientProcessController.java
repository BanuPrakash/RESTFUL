package com.adobe.asyncdemo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discharge")
public class PatientProcessController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // http://localhost:8080/api/discharge?id=5&name=Roger
    @GetMapping
    public String dischargePatient(@RequestParam("id") String id, @RequestParam("name") String name)  {
        eventPublisher.publishEvent(new PatientDischargeEvent(this, id, name));
        return "discharged!!";
    }
}
