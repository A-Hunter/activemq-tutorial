package producer;

import java.util.Random;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import com.google.gson.Gson;
import topics.DataUtil;

public class AMQMessageProducer {


    private static final String ACTION_ID = "actionId";
    private static final String ACTION = "action";

    private ConnectionFactory connFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer msgProducer;

    private String activeMqBrokerUri;
    private String username;
    private String password;

    public AMQMessageProducer(final String activeMqBrokerUri, final String username, final String password) {
        super();
        this.activeMqBrokerUri = activeMqBrokerUri;
        this.username = username;
        this.password = password;
    }

    /**
     * Wire connection, session with correct order.
     * Spring JMS Dependency Injection takes care of it for you.
     *
     * @param transacted
     * @param isDestinationTopic
     * @param destinationName
     * @throws JMSException
     */
    public void setup(final boolean transacted, final boolean isDestinationTopic, final String destinationName)
            throws JMSException {
        setConnectionFactory(activeMqBrokerUri, username, password);
        setConnection();
        setSession(transacted);
        setDestination(isDestinationTopic, destinationName);
        setMsgProducer();
    }

    /**
     * Close connection. Spring JMS takes care of it for you.
     *
     * @throws JMSException
     */
    public void close() throws JMSException {
        if (msgProducer != null) {
            msgProducer.close();
            msgProducer = null;
        }

        if (session != null) {
            session.close();
            session = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }

    }

    public void commit(final boolean transacted) throws JMSException {
        if (transacted) {
            session.commit();
        }
    }

    /**
     * Define the durability of message. All message are durable by default.
     * We can turn off to get better performance
     *
     * @param actionVal
     * @throws JMSException
     */
    public void sendMessage(final String actionVal) throws JMSException {
        TextMessage textMessage = buildTextMessageWithProperty(actionVal);
        msgProducer.send(destination, textMessage);
        /**
         * Define the durability of message. All message are durable by default.
         * We can turn off to get better performance
         */
        // msgProducer.send(textMessage, DeliveryMode.NON_PERSISTENT, 0, 0);
    }

    private TextMessage buildTextMessageWithProperty(final String action) throws JMSException {
        Gson gson = new Gson();
        String eventMsg = gson.toJson(DataUtil.buildClientEvent());
        TextMessage textMessage = session.createTextMessage(eventMsg);

        Random rand = new Random();
        int value = rand.nextInt(100);
        textMessage.setStringProperty(ACTION, action);
        textMessage.setStringProperty(ACTION_ID, String.valueOf(value));

        return textMessage;
    }

    private void setDestination(final boolean isDestinationTopic, final String destinationName) throws JMSException {
        if (isDestinationTopic) {
            destination = session.createTopic(destinationName);
        } else {
            destination = session.createQueue(destinationName);
        }
    }

    private void setMsgProducer() throws JMSException {
        msgProducer = session.createProducer(destination);

    }

    private void setSession(final boolean transacted) throws JMSException {
        // transacted=true for better performance to push message in batch mode
        session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
    }

    private void setConnection() throws JMSException {
        connection = connFactory.createConnection();
        connection.start();
    }

    private void setConnectionFactory(final String activeMqBrokerUri, final String username, final String password) {
        connFactory = new ActiveMQConnectionFactory(username, password, activeMqBrokerUri);

        ((ActiveMQConnectionFactory) connFactory).setUseAsyncSend(true);

        RedeliveryPolicy policy = ((ActiveMQConnectionFactory) connFactory).getRedeliveryPolicy();
        policy.setInitialRedeliveryDelay(500);
        policy.setBackOffMultiplier(2);
        policy.setUseExponentialBackOff(true);
        policy.setMaximumRedeliveries(2);
    }
}
