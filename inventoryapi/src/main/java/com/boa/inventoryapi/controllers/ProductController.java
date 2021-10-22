package com.boa.inventoryapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boa.inventoryapi.services.ProductService;
import com.boa.inventoryapi.models.Product;
import com.boa.inventoryapi.services.ProductDataPublisher;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductDataPublisher productDataPublisher;
	@PostMapping("/products")
	public ResponseEntity<?> addProduct(@RequestBody Product product){
		Product productObj=this.productService.saveProduct(product);
		if(productObj!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(productObj);
		}
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Not Added");
	}
	@GetMapping("/products/{productId}")
	public void publishProduct(@PathVariable("productId") long productId) {
		
	  this.productDataPublisher.sendMessage(productId);
		
	}
	
	@GetMapping("/products/fetch/{productId}")
	public Product getAllProduct(@PathVariable("productId") long productId) {
		
		return this.productService.fetchProductById(productId);
		
	}
}
