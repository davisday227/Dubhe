package com.panerai.users.consumer;

import com.panerai.users.config.PropertyUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerConfigFactory {
  private static Logger log = LoggerFactory.getLogger(ConsumerConfigFactory.class);

  @Getter
  @Setter
  static class ConsumerTopicTag {
    private String topic;

    private String tag;

    public ConsumerTopicTag(String topic, String tag) {
      this.topic = topic;
      this.tag = tag;
    }
  }

  private static Map<String, ConsumerTopicTag> topicTags;
  static {
    topicTags = new HashMap<>();
  }

  public static ConsumerTopicTag getTopicTag(Class clazz) {
    String name = clazz.getSimpleName();
    if (MapUtils.isEmpty(topicTags)) {
      // topic为空，从配置中加载进来
      String topic = PropertyUtil.getString("consumer." + name + ".topic");
      String tag = PropertyUtil.getString("consumer." + name + ".tag");
      if (StringUtils.isAnyBlank(topic, tag)) {
        throw new IllegalStateException("无法找到mq配置，无法启动");
      }

      topicTags.put(name, new ConsumerTopicTag(topic, tag));
    }

    return topicTags.get(name);
  }
}
