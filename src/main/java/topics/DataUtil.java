package topics;

import model.Event;

import java.util.Random;

public final class DataUtil {
	public static final String GROUP1_TOPIC = "group1.topic";
	
	public static final String GROUP1_QUEUE_1 = "group1.queue1";
	public static final String GROUP1_QUEUE_2 = "group1.queue2";
	
	public static final String GROUP2_QUEUE_1 = "group2.queue1";
	public static final String GROUP2_QUEUE_2 = "group2.queue2";
	
	public static final String TOPIC_SELECTOR = "topic.selector";

	public static final String VIRTUAL_TOPIC_CLIENT_TOPIC = "virtual.topic.client.topic";
	
	public static Event buildClientEvent() {
		Random rand = new Random();
		int value = rand.nextInt(100);
		Event event = new Event("NEWCLIENT", value);

		return event;
	}

}
