package com.boa.messagedemo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AskBOAHelper implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String question=(String) execution.getVariable("question");
		execution.getProcessEngineServices().getRuntimeService()
		.createMessageCorrelation("NeedAnswer")
		.setVariable("question", question)
		.correlate();
		
	}

}
