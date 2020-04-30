package com.chen.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {

    public static  final  String ACTIVEMQ_URL = "tcp://192.168.2.230:61616";
    public static  final  String QUEUE_NAME = "queue01";


    public static void main(String[] args) throws JMSException {
        //连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //通过连接工厂，获得connection
        Connection connection = activeMQConnectionFactory.createConnection();
        //启动
        connection.start();

        //创建会话 两个参数，第一个是事务，第二个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地（队列海曙主题）
        Queue queue = session.createQueue(QUEUE_NAME);
        //创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        //通过生产者生产3条信息发送到queue队列
        for (int i = 0; i < 9; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            //通过生产者发送给MQ
            producer.send(textMessage);
        }
        //释放资源
        producer.close();
        session.close();
        connection.close();

        System.out.println("消息发送到mq......");


    }

}
