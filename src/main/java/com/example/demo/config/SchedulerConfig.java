package com.example.demo.config;

import com.example.demo.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@Configuration
public class SchedulerConfig {
  @Autowired
  SimpMessagingTemplate template;

  @Scheduled(fixedDelay = 1000)
  public void sendAdhocMessages() {
    template.convertAndSend("/topic/user", new Greeting("Fixed Delay Scheduler"));
  }
}
