package com.tools.netty.client;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class TimeClientHandler extends SimpleChannelInboundHandler {
	private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
	private int counter;
	private byte[] req;
	
	
	public TimeClientHandler(){
		 req  =  ("Query Time Order"+System.getProperty("line.separator")).getBytes();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.warning("Unexpected exception from downstream:"+cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for(int i=0;i<100;i++){
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body = (String)msg;
		System.out.println("Now is:"+body+"; the counter is:"+(++counter));
	}
	
}
