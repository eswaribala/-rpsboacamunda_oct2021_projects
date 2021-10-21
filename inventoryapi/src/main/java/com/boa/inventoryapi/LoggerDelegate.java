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
@Component("logger")
public class LoggerDelegate implements JavaDelegate {
 @Autowired
  private RestTemplate restTemplate;
 @Value("${producturl}")
  private String productUrl;
  private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
  
  public void execute(DelegateExecution execution) throws Exception {
    
    LOGGER.info("\n\n  ... LoggerDelegate invoked by "
            + "activityName='" + execution.getCurrentActivityName() + "'"
            + ", activityId=" + execution.getCurrentActivityId()
            + ", processDefinitionId=" + execution.getProcessDefinitionId()
            + ", processInstanceId=" + execution.getProcessInstanceId()
            + ", businessKey=" + execution.getProcessBusinessKey()
            + ", executionId=" + execution.getId()
            + ", variables=" + execution.getVariables()
            + " \n\n");
    
    Product product=new Product();
    product.setName(execution.getVariable("name").toString());
    product.setDop(LocalDate.parse(execution.getVariable("dop").toString()));
    product.setCost(Long.parseLong(execution.getVariable("cost").toString()));
  //invoke rest service
  		HttpHeaders headers = new HttpHeaders();
  	       headers.setContentType(MediaType.APPLICATION_JSON);
  	    HttpEntity request = new HttpEntity<>(product,headers);

  		ResponseEntity<?> response=restTemplate.
  	 		      postForEntity(productUrl,request, String.class);

  		Map<String,Object> data=parseString(response.getBody().toString());
  		for(String key:data.keySet()) {
  			System.out.println(data.get(key));
  		}
  		
  		execution.setVariable("productId", data.get("productId"));
    
  }

  
  private Map<String,Object> parseString(String response)
	{
		JSONParser parser = new JSONParser(); 
		Map<String, Object> flattenedJsonMap=null;
		String token="";
	  	try {
	  		 
			// Put above JSON content to crunchify.txt file and change path location
			Object obj = parser.parse(response);
			JSONObject jsonObject = (JSONObject) obj;

			// JsonFlattener: A Java utility used to FLATTEN nested JSON objects
			String flattenedJson = JsonFlattener.flatten(jsonObject.toString());
			//log("\n=====Simple Flatten===== \n" + flattenedJson);

		flattenedJsonMap = JsonFlattener.flattenAsMap(jsonObject.toString());
		 	
		} catch (Exception e) {
			e.printStackTrace();
		}
	 return flattenedJsonMap;

	}
}
