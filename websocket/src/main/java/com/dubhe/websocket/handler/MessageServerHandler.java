package com.dubhe.websocket.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class MessageServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Logger log = LoggerFactory.getLogger(MessageServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        log.info("get message: {}", textWebSocketFrame.text());

        //send message back
        TextWebSocketFrame tws = new TextWebSocketFrame("got it");
        channelHandlerContext.channel().writeAndFlush(tws);
    }
}