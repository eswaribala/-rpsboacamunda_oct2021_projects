package com.boa.loanapplicationprocess.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
@Entity
@Table(name="Appointment")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Appointment_Id")
	private long appointmentId;
	@Column(name="Customer_Id")
	private long customerId;
	@Column(name="DOA")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate doa;
	@Column(name="Time")
	private String time;
	@Column(name="Reason")
	private String reason;

}
