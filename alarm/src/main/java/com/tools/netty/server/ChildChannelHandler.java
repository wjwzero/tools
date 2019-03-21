package com.tools.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChildChannelHandler extends
		ChannelInitializer<SocketChannel> {

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
		ch.pipeline().addLast(new StringDecoder());
		ch.pipeline().addLast(new TimeServerHandler());
	}
}