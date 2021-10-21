package com.boa.inventoryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boa.inventoryapi.models.Product;
import com.boa.inventoryapi.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
	private ProductRepository productRepository;
    
    
    public Product saveProduct(Product product) {
    	return productRepository.save(product);
    }
    
    public Product fetchProductById(long productId) {
    	return this.productRepository.findById(productId).orElse(null);
    }
}
