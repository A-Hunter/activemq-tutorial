package spring.jms.component;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

@Component
public class JmsExceptionListener implements ExceptionListener {

	private static final Logger LOG = Logger.getLogger(JmsExceptionListener.class);

	@Override
	public void onException(JMSException e) {
		String errorMessage = "Exception while processing the JMS message";
		LOG.error(errorMessage, e);		
	}

}
