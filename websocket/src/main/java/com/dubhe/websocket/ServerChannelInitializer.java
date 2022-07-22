package com.dubhe.websocket;


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
        // HttpServerCodec：将请求和应答消息解码为HTTP消息
        socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
        // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
        socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        // ChunkedWriteHandler：向客户端发送HTML5文件
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        //关闭后的消息处理
        socketChannel.pipeline().addLast("closeHandler", closeServerHandler);
        //初始化连接的处理handler
        socketChannel.pipeline().addLast("httpHandler", httpServerHandler);
        //消息处理handler
        socketChannel.pipeline().addLast("messageHandler", messageServerHandler);

    }
}