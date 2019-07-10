package com.tools.websocket.netty.handler;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tools.common.redis.RedisKey;
import com.tools.common.redis.RedisUtils;
import com.tools.websocket.netty.util.GlobalUserUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 *
 *
 * @author wangjianwei
 * @version 1.0
 * @date 2018/11/28 11:25
 */
@Component
@EnableJms
@ChannelHandler.Sharable
public class DefultNettyHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(DefultNettyHandler.class);
    private static final String URI = "websocket";
    private static final String PARAMS_VERIFY_USER_ID = "userId";
    private static final String UPGRADE = "Upgrade";
    private static final String WEBSOCKET = "websocket";
    private static final String WS_NETTY_CHANNEL = "ws:netty:channel";
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
    private RedisUtils redisUtils;

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

    /** 
     * 连接上服务器
     * @param ctx 
     * @return void 
     * @author  wangjianwei
     * @date 2019/1/17 20:39
     */ 
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
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

        // 可以对消息进行处理
        logger.debug("收到消息{}", ((TextWebSocketFrame) msg).text());
        //JSONObject obj = JSONObject.parseObject(((TextWebSocketFrame) msg).text());

        // 已建立连接根据参数做出操作
        /*if(tokenOauthVerifyNotPass(null, null)){
            //鉴权不通过关闭连接
            ctx.channel().writeAndFlush(new TextWebSocketFrame("更新操作鉴权不通过"));
            CloseWebSocketFrame c = new CloseWebSocketFrame();
            handshaker.close(ctx.channel(), c);
            return;
        }*/
        ChannelId channelId = ctx.channel().id();
        Set<String> channelSet = redisUtils.getKeys(WS_NETTY_CHANNEL +":"+"*");
        logger.info(channelSet.toString());
        ctx.channel().writeAndFlush(new TextWebSocketFrame(channelSet.toString()));
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

        //获取ws请求的参数
        /*Map<String, List<String>> parameters = webSocketUriParamsVerify(ctx, req);
        if (parameters == null) {
            return;
        }*/
        // TODO Oauth2 TOKEN鉴权

        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://" + req.headers().get("Host") + "/" + URI + "", null, false
        );
        handshaker = factory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }
        //进行连接
        handshaker.handshake(ctx.channel(), req);
        // TODO 判断对应规则关系是否存在,存在则更新 不存在则插入
        //获得当前的channelID
        ChannelId channelId = ctx.channel().id();

        String channelIdStr = ctx.channel().id().toString();
        Long groupId = 10000L;
        // TODO  根据握手param参数获得
        // 首次握手规则 redis key  value(序列化ChannelID)
        redisUtils.set(RedisKey.AlarmPush.CHANNEL_CACHE+":"+groupId+"_"+channelIdStr, ctx.channel().id());
        redisUtils.expire(RedisKey.AlarmPush.CHANNEL_CACHE+":"+groupId+"_"+channelIdStr,7, TimeUnit.DAYS);
        Set<String> channelSet = redisUtils.getKeys(RedisKey.AlarmPush.CHANNEL_CACHE+":"+"*"+"_"+channelIdStr);

        logger.info(channelSet.toString());
        // 首次握手返回消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("首次握手"));
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

    //@JmsListener(destination = "MQ_TOPIC_WEB_SOCKET_ALARM_INFO", containerFactory = "topicListenerFactory")
    public void consumer(byte[] msg) {
        //AlarmInfoBO message = JSONObject.parseObject(new String(msg), AlarmInfoBO.class);
        //logger.debug("获得MQ TOPIC推送信息{}",message);
        // PAAS因历史原因保存的关系字段为userId 在此抽象为关系Id
        //String relationId = message.getUserId();
        //String alarmType = message.getAlarmType();
//        Map<String, Set<ChannelId>> userIdChannelIdMap = cacheChannel.getIfPresent(relationId);
        // 分组下无用户无通道不存在
        /*if (CollectionUtils.isEmpty(userIdChannelIdMap)) {
            return;
        }*/
        // 用户下无通道,清除用户空通道,清除未读用户缓存
        /*userIdChannelIdMap.entrySet().removeIf(entry->{
            boolean emptySet = CollectionUtils.isEmpty(entry.getValue());
            if(emptySet){
                stringRedisTemplate.opsForHash().delete(RedisKey.AlarmPush.CHANNEL_UNREAD,String.valueOf(entry.getKey()));
            }
            return emptySet;
        });*/
        /*userIdChannelIdMap.forEach((userId,channelIdSet)->{
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
        });*/
    }

}
