package com.panerai.users.consumer;

import com.panerai.users.consumer.ConsumerConfigFactory.ConsumerTopicTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMqConsumer {
  private static Logger log = LoggerFactory.getLogger(AbstractMqConsumer.class);

  protected String topic;

  protected String tag;

  public AbstractMqConsumer() {
    ConsumerTopicTag topicTag = ConsumerConfigFactory.getTopicTag(this.getClass());
    this.topic = topicTag.getTopic();
    this.tag = topicTag.getTag();

    log.info("get topic: {} and tag: {}", this.topic, this.tag);
  }

  public String handle(String value) {
    log.info("this topic: {} and tag: {}", this.topic, this.tag);
    return externalHandle(value);
  }

  protected abstract String externalHandle(String value);

  public AbstractMqConsumer(String topic, String tag) {
    this.topic = topic;
    this.tag = tag;
  }
}
