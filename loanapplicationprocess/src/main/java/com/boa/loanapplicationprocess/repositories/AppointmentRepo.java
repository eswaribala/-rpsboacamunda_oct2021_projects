package com.boa.loanapplicationprocess.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boa.loanapplicationprocess.models.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment,Long>{

}
