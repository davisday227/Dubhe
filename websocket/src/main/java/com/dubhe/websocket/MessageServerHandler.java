package com.dubhe.websocket;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class MessageServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        System.out.println("我收到消息啦,收到了->" + textWebSocketFrame.text());
        //返回给服务端消息
        TextWebSocketFrame tws = new TextWebSocketFrame("我收到了");
        channelHandlerContext.channel().writeAndFlush(tws);
    }
}