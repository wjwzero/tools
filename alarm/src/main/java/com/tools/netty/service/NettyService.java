/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2018.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording,
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 *
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2018/11/28    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.netty.service;

import com.tools.netty.handler.AlarmNettyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2018/11/28 11:09
 */
@Component
public class NettyService {

    private static final Logger logger = LoggerFactory.getLogger(NettyService.class);

    @Value("${netty.server.port}")
    public Integer port;

    @Autowired
    private AlarmNettyHandler alarmNettyHandler;

    public void run() {
        //服务端需要2个线程组  boss处理客户端连接  work进行客服端连接之后的处理
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //服务器 配置
            bootstrap.group(boss, work).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // HttpServerCodec：将请求和应答消息解码为HTTP消息
                            socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
                            // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
                            socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
                            // ChunkedWriteHandler：向客户端发送HTML5文件
                            socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            // 进行设置心跳检测
                            socketChannel.pipeline().addLast(new IdleStateHandler(60, 30, 60 * 30, TimeUnit.SECONDS));
                            // 配置通道处理  来进行业务处理
                            socketChannel.pipeline().addLast(alarmNettyHandler);
                        }
                    });
            //绑定端口  开启事件驱动
            logger.info("【netty服务器启动成功========端口：{}】", port);
            Channel channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            logger.error("【netty服务启动异常========端口：{}】", port, e);
        } finally {
            //关闭资源
            boss.shutdownGracefully();
            work.shutdownGracefully();
            logger.info("【netty服务器关闭成功========端口：{}】", port);
        }
    }

}
