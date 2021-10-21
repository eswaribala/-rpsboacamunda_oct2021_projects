package com.boa.inventoryapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.boa.inventoryapi.models.Product;
import com.boa.inventoryapi.repositories.ProductRepository;

@Service
public class ProductDataPublisher {
	   @Autowired
		private ProductRepository productRepository;
	    
	    private boolean status;
	  //1. General topic with string payload
		
	  	@Value(value = "${product.topic.name}")
	      private String productTopicName;
	  	
	  	@Autowired
	      private KafkaTemplate<String,Product> kafkaTemplate;
	  	
	  	public void sendMessage(long productId) 
		{
	  		
			
	  		Product productObj=this.productRepository.findById(productId).orElse(null);
			if(productObj!=null) {
			ListenableFuture<SendResult<String, Product>> future 
				= this.kafkaTemplate.send(productTopicName, productObj);
			
			future.addCallback(new ListenableFutureCallback<SendResult<String, Product>>() {
	            @Override
	            public void onSuccess(SendResult<String, Product> result) {
	            	System.out.println("Sent message: " + productObj.getProductId() 
	            			+ " with offset: " + result.getRecordMetadata().offset());
	            	System.out.println("Sent message: " + productObj.getProductId()  
	            			+ " with offset: " + result.getRecordMetadata().offset());
	           // status=true;
	            }
	            

	            @Override
	            public void onFailure(Throwable ex) {
	            	System.out.println("Unable to send product Data : " + productObj
	            			.getProductId()+ex);
	              // status=false;
	            }
	       });
			}
			
		}
}
