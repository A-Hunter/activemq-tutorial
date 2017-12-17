package spring.app;

import java.net.URISyntaxException;

import configuration.JmsConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import spring.jms.component.BillingAppListener;

@Configuration
public class ConfigBillingForNewCustomerApp {

    public static void main(String[] args) throws URISyntaxException, Exception {
        Gson gson = new Gson();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JmsConfig.class);
        context.register(ConfigBillingForNewCustomerApp.class);

        try {

            BillingAppListener billingAppListener = (BillingAppListener) context.getBean("billingAppListener");

            System.out.println("ConfigBillingForewCustomerApp receives " + billingAppListener.receiveMessage());

        } finally {
            context.close();
        }
    }

}
