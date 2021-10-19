package com.boa.loanapplicationprocess;


  import java.util.logging.Logger;
  
  import org.camunda.bpm.engine.delegate.DelegateExecution; import
  org.camunda.bpm.engine.delegate.JavaDelegate; import
  org.springframework.stereotype.Component;
  
 /**
	 * This is an easy adapter implementation illustrating how a Java Delegate can
	 * be used from within a BPMN 2.0 Service Task.
	 */
		  @Component("failurelogger") 
		  public class FailureDelegate implements 		  JavaDelegate {
		  
		  private final Logger LOGGER =
		  Logger.getLogger(FailureDelegate.class.getName());
		  
		  public void execute(DelegateExecution execution) throws Exception {
		  
		  LOGGER.info("\n\n  ... Failure Delegate invoked by " + "activityName='" +
		  execution.getCurrentActivityName() + "'" + ", activityId=" +
		  execution.getCurrentActivityId() + ", processDefinitionId=" +
		  execution.getProcessDefinitionId() + ", processInstanceId=" +
		  execution.getProcessInstanceId() + ", businessKey=" +
		  execution.getProcessBusinessKey() + ", executionId=" + execution.getId() +
		  ", variables=" + execution.getVariables() + " \n\n");
		  
		  }
		  
		  }
		 