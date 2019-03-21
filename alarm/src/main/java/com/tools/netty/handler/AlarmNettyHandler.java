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


package com.tools.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tools.common.redis.RedisKey;
import com.tools.common.redis.RedisUtils;
import com.tools.netty.util.GlobalUserUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2018/11/28 11:25
 */
@Component
@EnableJms
@ChannelHandler.Sharable
public class AlarmNettyHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(AlarmNettyHandler.class);

    private static final String URI = "websocket";

    private static final String PARAMS_VERIFY_USER_ID = "userId";
    private static final String UPGRADE = "Upgrade";
    private static final String WEBSOCKET = "websocket";

    private volatile static AtomicInteger count = new AtomicInteger(0);

    private WebSocketServerHandshaker handshaker;

    /**
     * 分组id和websocket管道缓存
     *
     * 精简版推送缓存结构
     * 分组id-|-用户A-|-用户A管道id1
     *        |       |-用户A管道id2
     *        |       |-用户A管道id3
     *        |
     *        |-用户B-|-用户B管道id1
     *        |       |-用户B管道id2
     *        |       |-用户B管道id3
     */
    private static Cache<String, Map<String,Set<ChannelId>>> cacheChannel;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, byte[]> redisCacheTemplate;

    static {
        cacheChannel = CacheBuilder.newBuilder().maximumSize(50000).build();
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != HttpResponseStatus.OK.code()) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != HttpResponseStatus.OK.code()) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Resource
    private RedisUtils redisUtils;

    /** 
     * 连接上服务器
     * @param ctx 
     * @return void 
     * @author  wangjianwei
     * @date 2019/1/17 20:39
     */ 
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        String channelIdStr = ctx.channel().id().toString();
        Long groupId = 10000L;
        redisUtils.set(RedisKey.AlarmPush.CHANNEL_CACHE+":"+groupId+"_"+channelIdStr, ctx.channel().id());
        Set<String> channelSet = redisUtils.getKeys(RedisKey.AlarmPush.CHANNEL_CACHE+":"+"*"+"_"+channelIdStr);
        logger.info(channelSet.toString());
//        byte[] serializeTest =  serialize(ctx.channel().id());
//        redisTemplate.delete(RedisKey.AlarmPush.CHANNEL_CACHE);
       /* redisCacheTemplate.opsForValue().set(RedisKey.AlarmPush.CHANNEL_CACHE,serializeTest);*/
        /*ValueOperations<String, byte[]> valueOperations = redisCacheTemplate.opsForValue();
        byte[] serializeTest1 =  valueOperations.get(RedisKey.AlarmPush.CHANNEL_CACHE);*/
        Object serializeTest1 =  redisUtils.get(RedisKey.AlarmPush.CHANNEL_CACHE);
//        ChannelId channelIdTest = (ChannelId) unserizlize(serializeTest1);
//        ChannelId channelIdTest = (ChannelId)unserizlize(serializeTest);
        logger.debug("【handlerAdded】====>{}", ctx.channel().id());
        count.getAndIncrement();
        logger.debug("【当前连接数】====>{}", count.get());
        GlobalUserUtil.channels.add(ctx.channel());
    }

    /** 
     * 断开连接
     * @param ctx 
     * @return void 
     * @author  wangjianwei
     * @date 2019/1/17 20:39
     */ 
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ChannelId channelId = ctx.channel().id();
        logger.debug("【handlerRemoved】====>{}", ctx.channel().id());
        count.getAndDecrement();
        GlobalUserUtil.channels.remove(ctx);
        Long startTime = System.currentTimeMillis();
        // stream形式会全遍历
        cacheChannel.asMap().forEach((s, userIdChannelIdSetMap) -> userIdChannelIdSetMap.forEach((key, channelIdSet) -> channelIdSet.removeIf(channelId::equals)));
        logger.debug("缓存删除时间:{}ms",System.currentTimeMillis()-startTime);
    }

    /**
     * 连接异常   需要关闭相关资源
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("【系统异常】======>{}", cause.toString());
        /*ctx.close();
        ctx.channel().close();*/
        count.getAndDecrement();
        Channel ch = ctx.channel();
        ch.close();
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 活跃的通道  也可以当作用户连接上客户端进行使用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("【channelActive】=====>{}", ctx.channel());
    }

    /**
     * 不活跃的通道  就说明用户失去连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("【" + ctx.channel().remoteAddress() + "】不活跃通道!");
    }

    /**
     * 这里只要完成 flush
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 这里是保持服务器与客户端长连接  进行心跳检测 避免连接断开
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            PingWebSocketFrame ping = new PingWebSocketFrame();
            switch (stateEvent.state()) {
                //读空闲（服务器端）
                case READER_IDLE:
                    ctx.writeAndFlush(ping);
                    break;
                //写空闲（客户端）
                case WRITER_IDLE:
                    ctx.writeAndFlush(ping);
                    break;
                case ALL_IDLE:
                    logger.debug("【" + ctx.channel().remoteAddress() + "】读写空闲");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 收发消息处理
     * @param ctx
     * @param msg
     * @return void
     * @author  wangjianwei
     * @date 2019/1/17 20:38
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            doHandlerHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            doHandlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    /** 
     * websocket消息处理
     * @param ctx
     * @param msg 
     * @return void 
     * @author  wangjianwei
     * @date 2019/1/17 20:38
     */ 
    private void doHandlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        //判断msg 是哪一种类型  分别做出不同的反应
        if (msg instanceof CloseWebSocketFrame) {
            logger.debug("【关闭】");
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg.retain());
            return;
        }
        if (msg instanceof PingWebSocketFrame) {
            PingWebSocketFrame pong = new PingWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(pong);
            return;
        }
        if (msg instanceof PongWebSocketFrame) {
            PongWebSocketFrame ping = new PongWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(ping);
            return;
        }
        if (!(msg instanceof TextWebSocketFrame)) {
            logger.error("【不支持二进制】");
            throw new UnsupportedOperationException("不支持二进制");
        }

        //可以对消息进行处理
        logger.debug("收到消息{}", ((TextWebSocketFrame) msg).text());
        JSONObject obj = JSONObject.parseObject(((TextWebSocketFrame) msg).text());
        String token = obj.getString("token");
        String alarmTypes = obj.getString("alarmTypes");
        String userId = obj.getString("userId");
        int voice = obj.getIntValue("voice");
        if(tokenOauthVerifyNotPass(token, userId)){
            //鉴权不通过关闭连接
            ctx.channel().writeAndFlush(new TextWebSocketFrame("更新操作鉴权不通过"));
            CloseWebSocketFrame c = new CloseWebSocketFrame();
            handshaker.close(ctx.channel(), c);
            return;
        }
/*        // 通过鉴权则修改用户告警推送配置
        AlarmPushUserSetBO alarmPushUserSetBO = new AlarmPushUserSetBO(voice, alarmTypes);
        stringRedisTemplate.opsForHash().put(RedisKey.AlarmPush.USER_SETTING, userId, JSONObject.toJSONString(alarmPushUserSetBO));*/
        // 查看当前netty 连接信息
        String showGlobalGroupChannel = obj.getString("showGlobalGroupChannel");
        if("showGlobalGroupChannel".equals(showGlobalGroupChannel)){
            String channelUserId = Optional.ofNullable(obj.getString("channelUserId")).orElse(userId);
            String channelGroupId = Optional.ofNullable(obj.getString("channelGroupId")).orElse(userId);
            // 缓存分组数
            long cacheChanelSize = cacheChannel.size();
            long channelUserIdSize = cacheChannel.getIfPresent(channelGroupId).get(channelUserId).size();
            String str = String.format("当前netty缓存分组数%s;用户%s,当前连接通道数:%s",cacheChanelSize,channelUserId,channelUserIdSize);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(str));
        }
    }

    /** 
     * websocket 第一次握手连接
     * @param ctx
     * @param req 
     * @return void 
     * @author  wangjianwei
     * @date 2019/1/17 20:32
     */ 
    private void doHandlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // http 解码失败
        if (!req.decoderResult().isSuccess() || !WEBSOCKET.equals(req.headers().get(UPGRADE))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1,
                    HttpResponseStatus.BAD_REQUEST));
        }
        //请求方式为get
        if (req.method() != HttpMethod.GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FORBIDDEN));
            return;
        }

        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://" + req.headers().get("Host") + "/" + URI + "", null, false
        );
        handshaker = factory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }
        //进行连接
        handshaker.handshake(ctx.channel(), req);
        //获得当前的channelID
        ChannelId channelId = ctx.channel().id();
        // 从缓存中获得对应关系id的channelIds
        /*Map<String, Set<ChannelId>> userIdChannelIdMap = cacheChannel.getIfPresent(relationId);
        // 是否存在通道,不存在则新增，存在则追加
        if (CollectionUtils.isEmpty(userIdChannelIdMap)) {
            Map<String, Set<ChannelId>> userIdChannelIdNewMap = new HashMap<>(6);
            Set<ChannelId> channelIdSetNew = new HashSet<>();
            channelIdSetNew.add(channelId);
            userIdChannelIdNewMap.put(userId, channelIdSetNew);
            cacheChannel.put(relationId, userIdChannelIdNewMap);
        } else {
            Set<ChannelId> userChannelIdSet = userIdChannelIdMap.get(userId);
            if(userChannelIdSet == null || userChannelIdSet.size() ==0){
                Set<ChannelId> channelIdSetNew = new HashSet<>();
                channelIdSetNew.add(channelId);
                userIdChannelIdMap.put(userId,channelIdSetNew);
            }else{
                userChannelIdSet.add(channelId);
                userIdChannelIdMap.put(userId,userChannelIdSet);
            }
            cacheChannel.put(relationId, userIdChannelIdMap);
        }
        // 初始化计算阅读量 用户纬度
        int unReadNum = alarmDataService.alarmIsReadNum(relationId, alarmTypes);
        stringRedisTemplate.opsForHash().put(RedisKey.AlarmPush.CHANNEL_UNREAD, userId, String.valueOf(unReadNum));
        // 传输未读数
        String resJson = JSONObject.toJSONString(new PushAlarmDTO((long) unReadNum, ""));*/
        ctx.channel().writeAndFlush(new TextWebSocketFrame("连接成功"));
    }

    /**
     * OAuth2Token验证不通过ture
     * @param tokenValue
     * @param userId
     * @return boolean
     * @author  wangjianwei
     * @date 2019/1/17 21:07
     */
    private boolean tokenOauthVerifyNotPass(String tokenValue, String userId) {
        /*String redisTokenForUserId = Optional.ofNullable(stringRedisTemplate.opsForValue().get(RedisKey.Oauth.PREFIX_OAUTH2_USER_TO_ACCESS+userId)).orElseThrow(()->new ServiceException(AlarmWsErrorCodeEnum.ALARM_PUSH_WEB_SOCKET_TOKEN_ERROR)).toString();
        if(!tokenValue.equals(redisTokenForUserId)){
            logger.warn("token鉴权,用户ID{},传入TOKEN{},正确TOKEN{},对比结果{}", userId, tokenValue, redisTokenForUserId,tokenValue.equals(redisTokenForUserId));
        }
        return !tokenValue.equals(redisTokenForUserId);*/
        return true;
    }

    /**
     * 握手入参验证;不通过则返回null
     * @param ctx
     * @param req
     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     * @author  wangjianwei
     * @date 2019/1/17 20:37
     */
    private Map<String, List<String>> webSocketUriParamsVerify(ChannelHandlerContext ctx, FullHttpRequest req) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.uri());
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        if (parameters.size() == 0 || !parameters.containsKey(PARAMS_VERIFY_USER_ID)) {
            logger.info("缺少必要参数");
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return null;
        }
        return parameters;
    }

    /*@JmsListener(destination = MqConstants.MQ_TOPIC_WEB_SOCKET_ALARM_INFO, containerFactory = "topicListenerFactory")
    public void consumer(byte[] msg) {
        AlarmInfoBO message = JSONObject.parseObject(new String(msg), AlarmInfoBO.class);
        logger.debug("获得MQ TOPIC推送信息{}",message);
        // PAAS因历史原因保存的关系字段为userId 在此抽象为关系Id
        String relationId = message.getUserId();
        String alarmType = message.getAlarmType();
        Map<String, Set<ChannelId>> userIdChannelIdMap = cacheChannel.getIfPresent(relationId);
        // 分组下无用户无通道不存在
        if (CollectionUtils.isEmpty(userIdChannelIdMap)) {
            return;
        }
        // 用户下无通道,清除用户空通道,清除未读用户缓存
        userIdChannelIdMap.entrySet().removeIf(entry->{
            boolean emptySet = CollectionUtils.isEmpty(entry.getValue());
            if(emptySet){
                stringRedisTemplate.opsForHash().delete(RedisKey.AlarmPush.CHANNEL_UNREAD,String.valueOf(entry.getKey()));
            }
            return emptySet;
        });
        userIdChannelIdMap.forEach((userId,channelIdSet)->{
            // 清除失活通道
            channelIdSet.removeIf(channelId->{
                Channel channel  = GlobalUserUtil.channels.find(channelId);
                if (channel == null) {
                    // 此id不存在全局通道中，证明此通道id已经失活
                    logger.warn("捕获到失活chennl;chennlId:{},所属用户id:{}",channelId.toString(),userId);
                    return true;
                }
                return false;
            });
            // 用户告警推送配置
            AlarmPushUserSetBO alarmPushUserSetBO = alarmDataService.getAlarmPushUserSetBO(userId);
            String alarmTypes = alarmPushUserSetBO.getAlarmTypes();
            String[] alarmCodeList =  alarmTypes.split(StringUtils.COMMA);
            // 如果推送的告警代码为全部(-1) 或 不在用户设置中 则推送并计数
            if(ALARM_PUSH_USER_SETTING_ALL_CODE.equals(alarmTypes) || Arrays.asList(alarmCodeList).contains(alarmType)){
                // 新消息未读数加一
                Long unReadNum = stringRedisTemplate.opsForHash().increment(RedisKey.AlarmPush.CHANNEL_UNREAD, userId, 1);
                // 推送用户已连接的channel
                channelIdSet.forEach(channelId -> {
                    Channel channel  = GlobalUserUtil.channels.find(channelId);
                    String resJson = JSONObject.toJSONString(new PushAlarmDTO(unReadNum, new String(msg)));
                    channel.writeAndFlush(new TextWebSocketFrame(resJson));
                });
            }
        });
    }*/

    /**
     * 序列化
     */
    public static byte [] serialize(Object obj){
        ObjectOutputStream obi=null;
        ByteArrayOutputStream bai=null;
        try {
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt=bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                obi.close();
                bai.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 反序列化
     */
    public static Object unserizlize(byte[] byt){
        ObjectInputStream oii=null;
        ByteArrayInputStream bis=null;
        bis=new ByteArrayInputStream(byt);
        try {
            oii=new ObjectInputStream(bis);
            Object obj=oii.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oii.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
