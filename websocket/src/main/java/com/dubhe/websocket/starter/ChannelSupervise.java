package com.dubhe.websocket.starter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public final class ChannelSupervise {
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final ConcurrentHashMap<String, ChannelId> channelMap = new ConcurrentHashMap<>();

    public static void addChannel(String key, Channel channel) {
        channelGroup.add(channel);
        channelMap.put(key, channel.id());
    }

    public static Channel getChannel(String key) {
        ChannelId channelId = channelMap.get(key);
        if (channelId == null) {
            return null;
        }
        return channelGroup.find(channelId);
    }
}
