package com.example.activemq.demo.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 消息的消费者
public class JmsConsumer {

    // activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://192.168.253.130:61616";

    // 目的地的名称
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        javax.jms.Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5 创建消息的消费者  第一种方式
        MessageConsumer messageConsumer = session.createConsumer(queue);
        while (true) {
            // reveive() 一直等待接收消息，在能够接收到消息之前将一直阻塞。 是同步阻塞方式 。和socket的accept方法类似的。
            // reveive(Long time) : 等待n毫秒之后还没有收到消息，就是结束阻塞。
            // 因为消息发送者是 TextMessage，所以消息接受者也要是TextMessage
            TextMessage message = (TextMessage) messageConsumer.receive();
            if (null != message) {
                System.out.println("****消费者的消息：" + message.getText());
            } else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
