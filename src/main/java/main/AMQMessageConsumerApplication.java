package main;

import consumer.AMQMessageConsumer;
import topics.DataUtil;

import javax.jms.JMSException;

public class AMQMessageConsumerApplication {

    public static void main(String[] args) {
        consumeCustomerVTCQueue();
        consumerVTCQueueWithSelector();
        consumeGroup1Topic();
        consumeAllGroup2();
        consume_queue_with_prefetchsize();
    }

    private static void consumeCustomerVTCQueue() {
        // the message in the topic before this subscriber starts will not be
        // picked up.
        AMQMessageConsumer queueMsgListener = new AMQMessageConsumer("tcp://localhost:61616", "admin",
                "admin");
        //Consume from virtual topic queue Consumer.client.Virtual.Topic.Client.Topic
        queueMsgListener.setDestinationName("Consumer.client." + DataUtil.VIRTUAL_TOPIC_CLIENT_TOPIC);

        try {
            queueMsgListener.run();
        } catch (JMSException e) {

            e.printStackTrace();
        }
    }

    private static void consumerVTCQueueWithSelector() {
        AMQMessageConsumer queueMsgListener = new AMQMessageConsumer("tcp://localhost:61616", "admin",
                "admin");
        //Consume from virtual topic queue VTC.DZONE.JCG.Mary.Topic which message selector set as action='DZONE'
        queueMsgListener.setDestinationName("VTC.DZONE." + DataUtil.TOPIC_SELECTOR);
        queueMsgListener.setSelector("action='DZONE'");
        try {
            queueMsgListener.run();
        } catch (JMSException e) {

            e.printStackTrace();
        }
    }

    private static void consumeGroup1Topic() {
        AMQMessageConsumer queueMsgListener = new AMQMessageConsumer("tcp://localhost:61616", "admin",
                "admin");
        //Consume from topic group1.topic
        queueMsgListener.setDestinationName(DataUtil.GROUP1_TOPIC);
        queueMsgListener.setDestinationTopic(true);

        try {
            queueMsgListener.run();
        } catch (JMSException e) {

            e.printStackTrace();
        }
    }

    private static void consumeAllGroup2() {
        AMQMessageConsumer queueMsgListener = new AMQMessageConsumer("tcp://localhost:61616", "admin",
                "admin");
        //Consume from any queue name matches the *.group2.*"
        queueMsgListener.setDestinationName("*.group2.*");

        try {
            queueMsgListener.run();
        } catch (JMSException e) {

            e.printStackTrace();
        }
    }

    private static void exclusive_queue_Consumer() {
        AMQMessageConsumer queueMsgListener = new AMQMessageConsumer("tcp://localhost:61616", "admin",
                "admin");
        //Set exclusive message consumer. It will fail over if one consumer is down then the other will be picked to continue
        queueMsgListener.setDestinationName(DataUtil.GROUP2_QUEUE_2 + "?consumer.exclusive=true");

        try {
            queueMsgListener.run();
        } catch (JMSException e) {

            e.printStackTrace();
        }
    }

    private static void consume_queue_with_prefetchsize() {
        AMQMessageConsumer queueMsgListener = new AMQMessageConsumer("tcp://localhost:61616", "admin",
                "admin");
        //Set preFetch size for the consumer
        queueMsgListener.setDestinationName(DataUtil.GROUP1_QUEUE_2 + "?consumer.prefetchSize=10");

        try {
            queueMsgListener.run();
        } catch (JMSException e) {

            e.printStackTrace();
        }
    }

}
