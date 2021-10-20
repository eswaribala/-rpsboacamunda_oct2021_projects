package com.boa.loanapplicationprocess;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.history.UserOperationLogQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("RMTask")
public class RMTaskAssignmentListener implements JavaDelegate,TaskListener {
	@Autowired
	private HistoryService historyService;
  // TODO: Set Mail Server Properties
  protected static final String HOST = "mail.example.org";
  protected static final String USER = "admin@example.org";
  protected static final String PWD = "toomanysecrets";

  protected final static Logger LOGGER = Logger.getLogger(RMTaskAssignmentListener.class.getName());

  public void notify(DelegateTask delegateTask) {

    String assignee = delegateTask.getAssignee();
    String taskId = delegateTask.getId();

    if (assignee != null) {

      // Get User Profile from User Management
      IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
      User user = identityService.createUserQuery().userId(assignee).singleResult();

      if (user != null) {

        // Get Email Address from User Profile
        String recipient = user.getEmail();

        if (recipient != null && !recipient.isEmpty()) {

          Email email = new SimpleEmail();
          email.setCharset("utf-8");
          email.setHostName(HOST);
          email.setAuthentication(USER, PWD);

          try {
            email.setFrom("noreply@camunda.org");
            email.setSubject("Task assigned: " + delegateTask.getName());
            email.setMsg("Please complete: http://localhost:8080/camunda/app/tasklist/default/#/task=" + taskId);

            email.addTo(recipient);

            email.send();
            LOGGER.info(
                "Task Assignment Email successfully sent to user '" + assignee + "' with address '" + recipient + "'.");

          } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not send email to assignee", e);
          }

        } else {
          LOGGER.warning("Not sending email to user " + assignee + "', user has no email address.");
        }

      } else {
        LOGGER.warning("Not sending email to user " + assignee + "', user is not enrolled with identity service.");
      }

    }

  }

@Override
public void execute(DelegateExecution execution) throws Exception {
	// TODO Auto-generated method stub

    LOGGER.info("\n\n  ... RM LoggerDelegate invoked by "
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
