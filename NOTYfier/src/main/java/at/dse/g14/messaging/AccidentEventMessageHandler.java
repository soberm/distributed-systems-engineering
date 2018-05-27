package at.dse.g14.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Slf4j
@MessageEndpoint
public class AccidentEventMessageHandler implements MessageHandler {

    @Override
    @ServiceActivator(inputChannel = "accidentEventInputChannel")
    public void handleMessage(Message<?> message) throws MessagingException {
        log.info("AccidentEvent-Message arrived! Payload: {}", message.getPayload());
    }

}

