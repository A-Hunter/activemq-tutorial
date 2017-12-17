package spring.jms.component;


import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import model.Event;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import spring.service.BillingService;
import spring.service.MessageTransformer;
import topics.DataUtil;

@Component
public class BillingAppListener {


    @Autowired
    private JmsTemplate jmsQueueTemplate;

    @Autowired
    private BillingService billingService;

    @Autowired
    private MessageTransformer msgTransformer;

    private String queueName = "Consumer.Billing." + DataUtil.VIRTUAL_TOPIC_CLIENT_TOPIC;

    public String receiveMessage() throws JMSException {
        System.out.println(Thread.currentThread().getName() + ": BillingAppListener receiveMessage.");

        Destination destination = new ActiveMQQueue(queueName);
        TextMessage textMessage = (TextMessage) jmsQueueTemplate.receive(destination);

        Event customerEvt = msgTransformer.fromJson(textMessage.getText(), Event.class);
        return billingService.handleNewCustomer(customerEvt);
    }
}
