package com.panerai.users.consumer;

import com.panerai.users.annatation.MqConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MqConsumer
public class UserMqConsumer extends AbstractMqConsumer{
  private static Logger log = LoggerFactory.getLogger(UserMqConsumer.class);

  public UserMqConsumer() {
    super();
  }

  protected String externalHandle(String value) {
    return "user consumer: " + value;
  }
}
