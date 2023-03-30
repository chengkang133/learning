package com.example.activemq.demo.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * @auther: Junze
 * @date: 9:55 2022/6/12
 * @version: 1.0
 * @description:
 * ①　在事务性会话中，当一个事务被成功提交则消息被自动签收。如果事务回滚，则消息会被再次传送。事务优先于签收，开始事务后，签收机制不再起任何作用。
 * ②　非事务性会话中，消息何时被确认取决于创建会话时的应答模式。
 *
 */
// 持久化topic 的消息消费者
public class JmsConsummer_persistence {
    public static final String ACTIVEMQ_URL = "tcp://192.168.253.130:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        // 设置客户端ID。向MQ服务器注册自己的名称
        connection.setClientID("marry");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        // 创建一个topic订阅者对象。一参是topic，二参是订阅者名称
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark...");
        // 之后再开启连接
        connection.start();
        Message message = topicSubscriber.receive();
        while (null != message) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println(" 收到的持久化 topic ：" + textMessage.getText());
            message = topicSubscriber.receive();
        }
        session.close();
        connection.close();
    }
}
