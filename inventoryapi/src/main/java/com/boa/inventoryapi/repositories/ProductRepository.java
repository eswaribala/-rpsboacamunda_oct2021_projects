package com.boa.inventoryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boa.inventoryapi.models.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
