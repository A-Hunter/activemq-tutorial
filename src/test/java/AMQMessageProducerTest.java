
import javax.jms.JMSException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import producer.AMQMessageProducer;
import topics.DataUtil;

public class AMQMessageProducerTest {

    private AMQMessageProducer msgQueueSender;

    @Before
    public void setup() {
        msgQueueSender = new AMQMessageProducer("tcp://localhost:61616", "admin", "admin");
    }

    @After
    public void cleanup() throws JMSException {
        msgQueueSender.close();
    }

    /**
     * Send to queue group1.queue1
     * @throws JMSException
     */
    @Test
    public void send_msg_to_no_transaction_Queue() throws JMSException {
        msgQueueSender.setup(false, false, DataUtil.GROUP1_QUEUE_1);
        msgQueueSender.sendMessage("JCG");
    }

    /**
     * Send to queue group2.queue1
     * @throws JMSException
     */
    @Test
    public void send_msg_to_Group2_Queue1() throws JMSException {
        msgQueueSender.setup(false, false, DataUtil.GROUP2_QUEUE_1);
        msgQueueSender.sendMessage("JCG");
    }

    /**
     * Send to queue group1.queue2
     * @throws JMSException
     */
    @Test
    public void send_msg_to_transaction_Group1_Queue2() throws JMSException {
        msgQueueSender.setup(true, false, DataUtil.GROUP1_QUEUE_2);
        msgQueueSender.sendMessage("DEMO");
        msgQueueSender.commit(true);
    }

    /**
     * Send to normal topic group1.topic
     * @throws JMSException
     */
    @Test
    public void send_msg_to_no_transaction_Group1_Topic() throws JMSException {
        msgQueueSender.setup(false, true, DataUtil.GROUP1_TOPIC);
        msgQueueSender.sendMessage("MZHENG");
    }

    /**
     * Send to selector unaware topic Virtual.Topic.Client.Topic
     * @throws JMSException
     */
    @Test
    public void send_msg_to_Virtual_Topic() throws JMSException {
        msgQueueSender.setup(false, true, DataUtil.VIRTUAL_TOPIC_CLIENT_TOPIC);
        msgQueueSender.sendMessage("MZHENG");
    }

    /**
     * Send to selector aware topic Topic.Selector
     * @throws JMSException
     */
    @Test
    public void send_msg_to_Virtual_Topic_WithSelector() throws JMSException {
        msgQueueSender.setup(false, true, DataUtil.TOPIC_SELECTOR);
        msgQueueSender.sendMessage("DZONE");
    }
}
