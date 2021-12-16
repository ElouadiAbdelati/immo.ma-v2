/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms.producer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import jms.consumer.Consumer;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author ok
 */
public abstract  class Producer {
    private Producer(){
        
    }
    public static void sendMessage(String message) {
        try {
            ConnectionFactory connectionFactory =new ActiveMQConnectionFactory(
                    "tcp://localhost:61616"
            );
            
            Connection connection  =connectionFactory.createConnection();
            connection.start();
            Session session =connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("immo.queue");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage textMessage =session.createTextMessage();
            textMessage.setText(message);
            producer.send(textMessage);
            session.close();
        } catch (JMSException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
