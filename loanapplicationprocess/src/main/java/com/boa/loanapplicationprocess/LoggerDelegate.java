package com.boa.loanapplicationprocess;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.batch.Batch;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is an easy adapter implementation
 * illustrating how a Java Delegate can be used
 * from within a BPMN 2.0 Service Task.
 */
@Component("logger")
public class LoggerDelegate implements JavaDelegate {
@Autowired	
 private HistoryService historyService;
@Autowired	
 private RuntimeService runTimeService;
@Autowired 
private RepositoryService respositoryService;


@Autowired
 private RepositoryService repositoryService;
  private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
  
  public void execute(DelegateExecution execution) throws Exception {
    execution.setVariable("action", "Started");
	/*	
    repositoryService
    .createDeployment()
    .tenantId("tenant1")
    .addZipInputStream(null)
    .deploy();

    
    List<Deployment> deployments = repositoryService
    		  .createDeploymentQuery()
    		  .tenantIdIn("tenant1", "tenant2")
    		  .orderByTenantId()
    		  .asc()
    		  .list();
    */
    /*
		 * ProcessEngine processEngine = ProcessEngineConfiguration
		 * .createProcessEngineConfigurationFromResourceDefault()
		 * .setHistory(ProcessEngineConfiguration.HISTORY_FULL) .buildProcessEngine();
		 */
    //audit log
    //http://localhost:8080/engine-rest/history/activity-instance?processInstanceId={processInstanceId} 
    historyService.createHistoricProcessInstanceQuery()
    .finished()
    .processDefinitionId(execution.getProcessDefinitionId())
    .orderByProcessInstanceDuration().desc()
    .listPage(0, 10);
    HistoricProcessInstanceQuery query= historyService.createHistoricProcessInstanceQuery();
    Batch batch = historyService.setRemovalTimeToHistoricProcessInstances()
    		  .absoluteRemovalTime(new Date()) // sets an absolute removal time
    		   // .clearedRemovalTime()        // resets the removal time to null
    		   // .calculatedRemovalTime()     // calculation based on the engine's configuration
    		  .byQuery(query)
    		  .byIds(execution.getProcessInstanceId(), "...").executeAsync();
    		   // .hierarchical()              
    		  // sets a removal time across the hierarchy
    		
    historyService.createHistoricActivityInstanceQuery()
    .activityType("serviceTask")
    .processDefinitionId(execution.getProcessDefinitionId())
    .finished()
    .orderByHistoricActivityInstanceEndTime().desc()
    .listPage(0, 1);
    LOGGER.info("\n\n  ... LoggerDelegate invoked by "
            + "activityName='" + execution.getCurrentActivityName() + "'"
            + ", activityId=" + execution.getCurrentActivityId()
            + ", processDefinitionId=" + execution.getProcessDefinitionId()
            + ", processInstanceId=" + execution.getProcessInstanceId()
            + ", businessKey=" + execution.getProcessBusinessKey()
            + ", executionId=" + execution.getId()
            + ", variables=" + execution.getVariables()
            + " \n\n");
    
  }

}
