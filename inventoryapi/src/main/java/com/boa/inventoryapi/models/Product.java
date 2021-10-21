package com.boa.inventoryapi.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Entity
@Table(name="Product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Product_Id")
	private long productId;
    @Column(name="Product_Name")
	private String name;
    @Column(name="DOP")
    @DateTimeFormat(iso = ISO.DATE)
	private LocalDate dop;
    @Column(name="Product_Cost")
	private long cost;
	
}
