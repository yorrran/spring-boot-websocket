package com.example.demo.controller;

import com.example.demo.util.WebSocketServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/ws")
public class WebSocketController {

  @RequestMapping(value = "/sendAll", method = RequestMethod.GET)
  public String sendAllMessage(@RequestParam(required = true) String message) {
    try {
      WebSocketServer.BroadCastInfo(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "ok";
  }


  @RequestMapping(value = "/sendOne", method = RequestMethod.GET)
  public String sendOneMessage(@RequestParam(required = true) String message, @RequestParam(required = true) String id) {
    try {
      WebSocketServer.SendMessage(message, id);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "ok";
  }

}
