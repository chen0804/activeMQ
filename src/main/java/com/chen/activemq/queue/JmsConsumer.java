package com.chen.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumer {
    public static  final  String ACTIVEMQ_URL = "tcp://192.168.2.230:61616";
    public static  final  String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception {

        System.out.println("1号消费者");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        //启动
        connection.start();
        //创建会话 两个参数，第一个是事务，第二个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地（队列海曙主题）
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        /*while (true){
            //强制转换
            TextMessage message =(TextMessage) consumer.receive(4000L);//receive不带参数是一直等待消息
            if (message != null){
                System.out.println("消费者接受到消息:"+message.getText());
            }else {
                break;
            }

        }
        consumer.close();
        session.close();
        connection.close();*/

        //通过监听的方式来消费消息,
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();



    }

}
