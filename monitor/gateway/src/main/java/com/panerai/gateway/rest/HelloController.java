package com.panerai.gateway.rest;

import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  private static Logger log = LoggerFactory.getLogger(HelloController.class);

  private AtomicLong count = new AtomicLong();

  @GetMapping("/hello")
  public String sayHello(String name) {
    log.info("count: {}", count.getAndIncrement());
    return "hello, " + name;
  }

}
