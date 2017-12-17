package spring.service;

import model.Event;
import org.springframework.stereotype.Service;

@Service
public class BillingService {
	public String handleNewCustomer(Event customerEvt){
		String message = "BillingService handleNewCustomer " + customerEvt.toString();
		return message;
	}
}
