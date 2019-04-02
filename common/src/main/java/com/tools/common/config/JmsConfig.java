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
 * 2018年12月27日    yaojianping         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.config;

import com.tools.common.constant.MqCommonConstants;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.DeliveryMode;
import javax.jms.Topic;

/**
 * ActiveMq配置
 *
 * @author yaojianping
 * @version 1.0
 * @date 2018年12月27日 下午5:13:38
 */
@Configuration
@EnableJms
public class JmsConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerURL;

    @Value("${spring.activemq.user}")
    private String userName;

    @Value("${spring.activemq.password}")
    private String password;

    /**
     * ActiveMQ连接工厂，非@Bean创建，配置互不影响
     *
     * @return org.apache.activemq.ActiveMQConnectionFactory
     * @author yaojianping
     * @date 2018/12/27 11:29
     */
    private ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerURL);
        factory.setUserName(userName);
        factory.setPassword(password);

        // 重发策略
        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
        // 初始重发延迟时间
        queuePolicy.setInitialRedeliveryDelay(0);
        // 重发延迟时间，当initialRedeliveryDelay=0时生效
        queuePolicy.setRedeliveryDelay(1000);
        // 启用指数倍数递增的方式增加延迟时间
        queuePolicy.setUseExponentialBackOff(false);
        // 重发次数
        queuePolicy.setMaximumRedeliveries(3);
        factory.setRedeliveryPolicy(queuePolicy);
        return factory;
    }

    /**
     * 消费者策略
     *
     * @return org.apache.activemq.ActiveMQPrefetchPolicy
     * @author yaojianping
     * @date 2018/12/27 15:29
     */
    @Bean
    ActiveMQPrefetchPolicy perfetchPolicy() {
        ActiveMQPrefetchPolicy perfetchPolicy = new ActiveMQPrefetchPolicy();
        perfetchPolicy.setQueuePrefetch(1);
        perfetchPolicy.setQueueBrowserPrefetch(1);
        perfetchPolicy.setDurableTopicPrefetch(100);
        perfetchPolicy.setTopicPrefetch(32766);
        return perfetchPolicy;
    }

    /**
     * 处理topic类型工厂，只有一个消费者<br/>
     * 示例：@JmsListener(destination = "queue.TEST", containerFactory="topicListenerFactory")
     *
     * @return org.springframework.jms.config.DefaultJmsListenerContainerFactory
     * @author yaojianping
     * @date 2018/12/27 14:45
     */
    @Bean(name = "topicListenerFactory")
    public DefaultJmsListenerContainerFactory topicListenerFactory(ActiveMQPrefetchPolicy perfetchPolicy) {
        ActiveMQConnectionFactory connectionFactory = connectionFactory();
        connectionFactory.setPrefetchPolicy(perfetchPolicy);

        // 使用缓存工厂连接
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(new CachingConnectionFactory(connectionFactory));
        factory.setPubSubDomain(true);
        // 消费者数量
        factory.setConcurrency("1");
        return factory;
    }

    /**
     * 处理queue类型工厂<br/>
     * 示例：@JmsListener(destination = "queue.TEST", containerFactory="queueListenerFactory", concurrency = "1-50")
     *
     * @return org.springframework.jms.config.DefaultJmsListenerContainerFactory
     * @author yaojianping
     * @date 2018/12/27 14:45
     */
    @Bean(name = "queueListenerFactory")
    public DefaultJmsListenerContainerFactory queueListenerFactory(ActiveMQPrefetchPolicy perfetchPolicy) {
        ActiveMQConnectionFactory connectionFactory = connectionFactory();
        connectionFactory.setPrefetchPolicy(perfetchPolicy);

        // 使用缓存工厂连接
        CachingConnectionFactory cacheFactory = new CachingConnectionFactory(connectionFactory);
        cacheFactory.setSessionCacheSize(200);

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cacheFactory);
        factory.setPubSubDomain(false);
        return factory;
    }

    /**
     * jmsTemplate用于消息发送
     *
     * @return org.springframework.jms.core.JmsTemplate
     * @author yaojianping
     * @date 2018/12/27 15:03
     */
    @Bean
    public JmsTemplate jmsTemplate() {
        ActiveMQConnectionFactory connectionFactory = connectionFactory();
        // 启用优化ACK选项
        connectionFactory.setOptimizeAcknowledge(true);
        // 启用异步发送，非持久化默认异步
        connectionFactory.setUseAsyncSend(true);
        // 启用消息体的压缩
        connectionFactory.setUseCompression(true);
        // 每次都等待服务器端的回执
        connectionFactory.setAlwaysSyncSend(false);

        CachingConnectionFactory cacheFactory = new CachingConnectionFactory(connectionFactory);
        cacheFactory.setSessionCacheSize(100);
        JmsTemplate jmsTemplate = new JmsTemplate(cacheFactory);
        // 消息持久化
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        return jmsTemplate;
    }

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(MqCommonConstants.MQ_TOPIC_WEB_SOCKET_ALARM_INFO);
    }

}
