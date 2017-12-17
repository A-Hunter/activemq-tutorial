package spring.app;

import java.net.URISyntaxException;

import configuration.JmsConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import spring.jms.component.SupportAppListener;

@Configuration
public class ConfigSupportForNewCustomerApp {

    public static void main(String[] args) throws URISyntaxException, Exception {
        Gson gson = new Gson();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JmsConfig.class);
        context.register(ConfigSupportForNewCustomerApp.class);

        try {
            SupportAppListener supportAppListener = (SupportAppListener) context.getBean("supportAppListener");
            System.out.println("supportAppListener receives " + supportAppListener.receiveMessage());

        } finally {
            context.close();
        }
    }
}
