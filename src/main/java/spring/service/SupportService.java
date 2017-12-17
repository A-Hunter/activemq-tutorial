package spring.service;

import model.Event;
import org.springframework.stereotype.Service;

@Service
public class SupportService {

	public String handleNewCustomer(Event customerEvt) {
		String message = "SupportService handleNewCustomer " + customerEvt.toString();
		return message;
	}

}
