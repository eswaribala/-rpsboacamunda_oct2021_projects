package com.boa.loanapplicationprocess.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boa.loanapplicationprocess.models.Appointment;
import com.boa.loanapplicationprocess.repositories.AppointmentRepo;

@Service
public class AppointmentService {
    @Autowired
	private AppointmentRepo appointmentRepo;
    
    
    public Appointment saveAppointment(Appointment appointment) {
    	return appointmentRepo.save(appointment);
    }
}
