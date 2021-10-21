package com.boa.inventoryapi;

import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.boa.inventoryapi.models.Product;
import com.github.wnameless.json.flattener.JsonFlattener;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * This is an easy adapter implementation
 * illustrating how a Java Delegate can be used
 * from within a BPMN 2.0 Service Task.
 */
@Component("publishdatadelegate")
public class PublishDataDelegate implements JavaDelegate {
 @Autowired
  private RestTemplate restTemplate;
 @Value("${producturl}")
  private String productUrl;
  private final Logger LOGGER = Logger.getLogger(PublishDataDelegate.class.getName());
  
  public void execute(DelegateExecution execution) throws Exception {
    
    LOGGER.info("\n\n  ... Publish Data Delegate invoked by "
            + "activityName='" + execution.getCurrentActivityName() + "'"
            + ", activityId=" + execution.getCurrentActivityId()
            + ", processDefinitionId=" + execution.getProcessDefinitionId()
            + ", processInstanceId=" + execution.getProcessInstanceId()
            + ", businessKey=" + execution.getProcessBusinessKey()
            + ", executionId=" + execution.getId()
            + ", variables=" + execution.getVariables()
            + " \n\n");
    
    long productId=Long.parseLong(execution.getVariable("productId")
			.toString());
	HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
  
 	
	HttpEntity request = new HttpEntity<String>(null,headers);

	ResponseEntity<?> responseEntityStr = restTemplate.
	          exchange(productUrl+"/"+productId,HttpMethod.GET, request,
			  String.class); 

	LOGGER.info("Data published");
  }

  
  
}
