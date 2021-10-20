package com.boa.loanapplicationprocess.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boa.loanapplicationprocess.models.Appointment;
import com.boa.loanapplicationprocess.services.AppointmentService;

@RestController
public class AppointmentController {
    @Autowired
	private AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<?> addAppointment(@RequestBody Appointment appointment){
    	if(appointmentService.saveAppointment(appointment)!=null) {
    		return ResponseEntity.status(HttpStatus.ACCEPTED).body(appointment);
    	}
    	else
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Saved");
    }
    
}
