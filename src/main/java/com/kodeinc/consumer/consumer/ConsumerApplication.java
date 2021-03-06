package com.kodeinc.consumer.consumer;

import java.util.logging.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;

@SpringBootApplication
@EnableBinding(ConsumerChannels.class)
public class ConsumerApplication {

    @StreamListener(target = ConsumerChannels.GREETING)
    public void handleMessage(String message) {
        System.out.println("Subscriber Received Message is: " + message);
    }

    /*
     @Bean
     IntegrationFlow integrationFlow(Logger logger, ConsumerChannels c) {
     return IntegrationFlows.from(c.producer())
     .handle(String.class, (payload, header) -> {
     logger.info("New Message "+ payload);
     return null;
     })
     .get(); 
     }
     */
    @Bean
    @Scope("prototype")
    Logger logger(InjectionPoint ip) {
        return Logger.getLogger(ip.getDeclaredType().getName());
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}

interface ConsumerChannels {

    String GREETING = "greetings";

    @Input(GREETING)
    SubscribableChannel producer();
}
