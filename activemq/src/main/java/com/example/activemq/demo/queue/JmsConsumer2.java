package com.example.activemq.demo.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 消息的消费者
public class JmsConsumer2 {

    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";

    // 目的地的名称
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5 创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        // 第二种方式
        /* 通过监听的方式来消费消息，是异步非阻塞的方式消费消息。
           通过messageConsumer 的setMessageListener 注册一个监听器，当有消息发送来时，系统自动调用MessageListener 的 onMessage 方法处理消息
         */
        messageConsumer.setMessageListener(message -> {
            //  instanceof 判断是否A对象是否是B类的子类
            if (null != message && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("****消费者的消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 让主线程不要结束。因为一旦主线程结束了，其他的线程（如此处的监听消息的线程）也都会被迫结束。
        // 实际开发中，我们的程序会一直运行，这句代码都会省略。
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
