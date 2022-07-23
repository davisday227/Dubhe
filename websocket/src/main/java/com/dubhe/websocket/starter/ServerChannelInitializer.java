package com.dubhe.websocket.starter;


import com.dubhe.websocket.handler.MessageServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private CloseServerHandler closeServerHandler;
    @Autowired
    private HttpServerHandler httpServerHandler;
    @Autowired
    private MessageServerHandler messageServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
        socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        socketChannel.pipeline().addLast("closeHandler", closeServerHandler);
        socketChannel.pipeline().addLast("httpHandler", httpServerHandler);
        socketChannel.pipeline().addLast("messageHandler", messageServerHandler);

    }
}