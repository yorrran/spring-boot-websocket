package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  @Bean
  public ServerEndpointExporter serverEndpointExporter(){
    System.out.println("injected websocket");
    return new ServerEndpointExporter();
  }
}
