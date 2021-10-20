package com.boa.loanapplicationprocess;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication("upload_process")
public class CamundaApplication {
   
  public static void main(String... args) {
    SpringApplication.run(CamundaApplication.class, args);
  
  }

  
}
