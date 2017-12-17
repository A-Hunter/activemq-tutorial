package spring.jms.component;


import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import model.Event;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import spring.service.MessageTransformer;
import spring.service.SupportService;
import topics.DataUtil;

@Component
public class SupportAppListener {


    @Autowired
    private JmsTemplate jmsQueueTemplate;

    @Autowired
    private SupportService supportService;

    @Autowired
    private MessageTransformer msgTransformer;

    private String queueName = "Consumer.Support." + DataUtil.VIRTUAL_TOPIC_CLIENT_TOPIC;

    public String receiveMessage() throws JMSException {
        System.out.println(Thread.currentThread().getName() + ": SupportAppListener receiveMessage." );

        Destination destination = new ActiveMQQueue(queueName);
        TextMessage textMessage = (TextMessage) jmsQueueTemplate.receive(destination);

        Event customerEvt = msgTransformer.fromJson(textMessage.getText(), Event.class);
        return supportService.handleNewCustomer(customerEvt);
    }
}
