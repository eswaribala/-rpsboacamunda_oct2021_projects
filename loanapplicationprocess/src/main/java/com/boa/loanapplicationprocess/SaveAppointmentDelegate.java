package com.boa.loanapplicationprocess;

import java.time.LocalDate;
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

import com.boa.loanapplicationprocess.models.Appointment;

/**
 * This is an easy adapter implementation
 * illustrating how a Java Delegate can be used
 * from within a BPMN 2.0 Service Task.
 */
@Component("saveappointment")
public class SaveAppointmentDelegate implements JavaDelegate {
 @Autowired
  private RestTemplate restTemplate;	
  @Value("${url}")
  private String url;
  private final Logger LOGGER = Logger.getLogger(SaveAppointmentDelegate.class.getName());
  
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
    
    Appointment appointment=new Appointment();	
    appointment.setCustomerId(Long.parseLong(execution.getVariable("customerId").toString()));  
    appointment.setDoa(LocalDate.parse(execution.getVariable("doa").toString()));
    appointment.setTime(execution.getVariable("time").toString());
    appointment.setReason(execution.getVariable("reason").toString());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity request = new HttpEntity<>(appointment,headers);
    //delegate to rest service
    ResponseEntity<?> response=restTemplate.
		      postForEntity(url,request, String.class);
  }
  
  
}
