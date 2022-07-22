package com.dubhe.websocket;


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
    //调度线程组,负责接收请求,不实际处理业务
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    //工作线程组,负责实际处理请求
    EventLoopGroup workGroup = new NioEventLoopGroup();
    private Logger log = LoggerFactory.getLogger(NettyServer.class);

    //自定义服务器通道初始化器,后面创建该类
    @Autowired
    private ServerChannelInitializer serverChannelInitializer;

    public void start() {
        //定义websocket 端口
        InetSocketAddress socketAddress = new InetSocketAddress(9999);
        //创建主线程一个主线程
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                //指定通道类型
                .channel(NioServerSocketChannel.class)
                //指定初始化器
                .childHandler(serverChannelInitializer)
                //指定初始化端口
                .localAddress(socketAddress)
                //设置队列大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            //绑定端口,开始接收进来的连接
            ChannelFuture future = bootstrap.bind(socketAddress).sync();
            log.info("服务器启动开始监听端口: {}", socketAddress.getPort());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        log.info("关闭Netty");

    }
}