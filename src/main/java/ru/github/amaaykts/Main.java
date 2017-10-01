package ru.github.amaaykts;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Main {
    private static String username = "admin";
    private static String password = "admin";
    private static String url = "tcp://localhost:61616";
    private static String queueName = "My Queue";
    private static String textMessage = "I'm alive!";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        Message message = session.createTextMessage(textMessage);
        producer.send(message);

        MessageConsumer consumer = session.createConsumer(destination);
        TextMessage receiveMessage = (TextMessage) consumer.receive(1000);
        System.out.println(receiveMessage.getText());
        session.close();
        connection.close();
    }
}
