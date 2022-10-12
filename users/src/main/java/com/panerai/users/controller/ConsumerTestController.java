package com.panerai.users.controller;

import com.panerai.users.consumer.UserMqConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerTestController {

  @Autowired
  private UserMqConsumer consumer;

  @GetMapping("/topic")
  public String test() {
    return consumer.handle("davis");
  }
}
