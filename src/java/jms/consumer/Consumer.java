/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jms.consumer;

import bean.helper.JmsMessage;
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import util.EmailUtils;

/**
 *
 * @author ok
 */

public class Consumer {

    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:61616"
            );

            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("immo.queue");
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            Gson gson = new Gson();
                            TextMessage textMessage = (TextMessage) message;
                            JmsMessage jmsMessage = gson.fromJson(textMessage.getText(), JmsMessage.class);
                            System.out.println(jmsMessage.toString());
                            String body = "";
                            if (jmsMessage.getStatus()) {
                                body = "Votre annonce qui a comme reference " + jmsMessage.getReferenceAnnonce() + " a été accepté avec succeé.";
                            } else  {
                                body = "Votre annonce qui a comme reference " + jmsMessage.getReferenceAnnonce() + " n'a pas été accepté. \n\n";
                                body += "Remarques : \n" + message;
                            }
                            EmailUtils.sendMail(jmsMessage.getAnnonceurEmail(), "Resultat de la demande de validation de l'annonce " + jmsMessage.getReferenceAnnonce(), body);
                        } catch (JMSException ex) {
                            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        } catch (JMSException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
