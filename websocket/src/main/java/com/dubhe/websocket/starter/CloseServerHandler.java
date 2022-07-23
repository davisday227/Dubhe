package com.dubhe.websocket.starter;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class CloseServerHandler extends SimpleChannelInboundHandler<CloseWebSocketFrame> {

    private static Logger log = LoggerFactory.getLogger(CloseServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CloseWebSocketFrame closeWebSocketFrame) throws Exception {
        log.info("closing netty server");
    }
}