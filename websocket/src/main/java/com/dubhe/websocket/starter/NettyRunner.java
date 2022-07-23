package com.dubhe.websocket.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NettyRunner implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(NettyRunner.class);

    @Autowired
    private NettyServer nettyServer;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting netty runner");
        new Thread(() -> {
            nettyServer.start();
        }).start();

    }
}