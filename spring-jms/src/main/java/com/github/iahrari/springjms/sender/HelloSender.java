package com.github.iahrari.springjms.sender;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iahrari.springjms.config.JmsConfig;
import com.github.iahrari.springjms.model.HelloWorldMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public record HelloSender(
    JmsTemplate jmsTemplate,
    ObjectMapper objectMapper
) {
    
    // @Scheduled(fixedRate = 2000)
    public void send() {

        var message = HelloWorldMessage.builder()
            .id(UUID.randomUUID())
            .message("Hello World")
            .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceive() 
        throws JMSException, 
                JsonMappingException, 
                JsonProcessingException 
    {

        var message = HelloWorldMessage.builder()
            .id(UUID.randomUUID())
            .message("Hello")
            .build();

        var receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.SEND_REC_QUEUE, (session) -> {
            Message helloMessage = null;
            try{
                helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                helloMessage.setStringProperty("_type", "com.github.iahrari.springjms.model.HelloWorldMessage");

                System.out.println("Sending message: Hello");
                return helloMessage;
            } catch(JsonProcessingException e){
                throw new JMSException("boom");
            }
        });

        var body = receivedMessage.getBody(String.class);
        var mapped = objectMapper.readValue(body, HelloWorldMessage.class);
        System.out.println("Received message: " + mapped);
    }
}

