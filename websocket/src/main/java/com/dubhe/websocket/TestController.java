package com.dubhe.websocket;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestController {
    private static Logger log = LoggerFactory.getLogger(TestController.class);

    private AtomicInteger index = new AtomicInteger(0);

    @GetMapping("/hello")
    public void sayHello() {
        Channel channel = ChannelSupervise.getChannel("11111");
        log.info("get channel: {}", channel);
        if (channel != null) {
            log.info("向客户端发消息");
            TextWebSocketFrame tws = new TextWebSocketFrame("hello" + index.getAndIncrement());
            channel.writeAndFlush(tws);
        }

    }
}
