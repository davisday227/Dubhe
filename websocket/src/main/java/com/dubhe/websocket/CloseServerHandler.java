package com.dubhe.websocket;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class CloseServerHandler extends SimpleChannelInboundHandler<CloseWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CloseWebSocketFrame closeWebSocketFrame) throws Exception {
        System.out.println("我要关闭啦");
    }
}