package main;

import producer.AMQMessageProducer;
import topics.DataUtil;

public class OnBoardNewClientApplication {

    public static void main(String[] args) {
        AMQMessageProducer producer = new AMQMessageProducer("tcp://localhost:61616", "admin", "admin");
        try{
            producer.setup(false, true, DataUtil.VIRTUAL_TOPIC_CLIENT_TOPIC);
            producer.sendMessage("CLIENT");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
