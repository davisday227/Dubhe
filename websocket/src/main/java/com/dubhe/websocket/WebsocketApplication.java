package com.dubhe.websocket;

import com.dubhe.websocket.starter.ChannelConst;
import com.dubhe.websocket.starter.ChannelSupervise;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class WebsocketApplication {

    private static Logger log = LoggerFactory.getLogger(WebsocketApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }

    AtomicInteger index = new AtomicInteger(0);

    /**
     * For test, send message to client per 10 seconds.
     */
    @PostConstruct
    public void test() {
        new Thread(() -> {
            while (true) {
                Channel channel = ChannelSupervise.getChannel(ChannelConst.CHANNEL_KEY);
                log.info("get channel: {}", channel);
                if (channel != null) {
                    log.info("send message to channel 11111");
                    TextWebSocketFrame tws = new TextWebSocketFrame("hello" + index.getAndIncrement());
                    channel.writeAndFlush(tws);
                }
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
