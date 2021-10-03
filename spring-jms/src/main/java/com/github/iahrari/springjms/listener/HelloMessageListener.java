package com.github.iahrari.springjms.listener;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import com.github.iahrari.springjms.config.JmsConfig;
import com.github.iahrari.springjms.model.HelloWorldMessage;

import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public record HelloMessageListener(
    JmsTemplate jmsTemplate
) {
    
    // @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(
        @Payload HelloWorldMessage helloWorldMessage, 
        @Headers MessageHeaders headers, Message message){
            System.out.println("Received <" + helloWorldMessage + ">");
    }

    @JmsListener(destination = JmsConfig.SEND_REC_QUEUE)
    public void listenAndAnswer(
        @Payload HelloWorldMessage helloWorldMessage, 
        @Headers MessageHeaders headers, 
        Message message
    ) throws JmsException, JMSException{
        // System.out.println("Received <" + helloWorldMessage + ">");
        var helloMessage = HelloWorldMessage.builder()
            .id(UUID.randomUUID())
            .message("Hello " + helloWorldMessage.getMessage())
            .build();

       jmsTemplate.convertAndSend((Destination) message.getJMSReplyTo(), helloMessage);
    }
}


