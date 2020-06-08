package com.kodeinc.consumer.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.SubscribableChannel;

@SpringBootApplication
@EnableBinding(ConsumerChannels.class)
public class ConsumerApplication {

    IntegrationFlow integrationFlow(ConsumerChannels c) {
        return IntegrationFlows.from(c.producer())
                .handle(String.class, (payload, header) -> {
                    return null;
                })
                .get();

    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}

interface ConsumerChannels {

    @Input
    SubscribableChannel producer();
}
