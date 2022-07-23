package com.dubhe.websocket.starter;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
public class NettyServer {
    private Logger log = LoggerFactory.getLogger(NettyServer.class);

    EventLoopGroup bossGroup = new NioEventLoopGroup();

    EventLoopGroup workGroup = new NioEventLoopGroup();

    @Autowired
    private ServerChannelInitializer serverChannelInitializer;

    public void start() {
        log.info("starting netty server");
        InetSocketAddress socketAddress = new InetSocketAddress(9999);
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(serverChannelInitializer)
                .localAddress(socketAddress)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture future = bootstrap.bind(socketAddress).sync();
            log.info("starting server on port: {}", socketAddress.getPort());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("fail to start server", e);
        } finally {
            //关闭主线程组
            bossGroup.shutdownGracefully();
            //关闭工作线程组
            workGroup.shutdownGracefully();
        }
    }


    @PreDestroy
    public void destory() throws InterruptedException {
        bossGroup.shutdownGracefully().sync();
        workGroup.shutdownGracefully().sync();
        log.info("close Netty");

    }
}