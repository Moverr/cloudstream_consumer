package com.kodeinc.consumer.consumer;

import java.util.logging.Logger;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.SubscribableChannel;

@SpringBootApplication
@EnableBinding(ConsumerChannels.class)
public class ConsumerApplication {
    private static final Logger LOG = Logger.getLogger(ConsumerApplication.class.getName());

    
    @Bean
    IntegrationFlow integrationFlow(ConsumerChannels c) {
        return IntegrationFlows.from(c.producer())
                .handle(String.class, (payload, header) -> {
                    LOG.info("New Message "+ payload);
                    return null;
                })
                .get(); 
    }
    
    Logger logger(InjectionPoint ip){
        return Logger.getLogger(ip.getDeclaredType().getName());
    }
     

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}

interface ConsumerChannels {

    @Input
    SubscribableChannel producer();
}
