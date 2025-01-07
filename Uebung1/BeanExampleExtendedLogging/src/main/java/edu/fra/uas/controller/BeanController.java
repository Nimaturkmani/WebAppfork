package edu.fra.uas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.BeanExampleApplication;
import edu.fra.uas.service.MessageService;

@Component
public class BeanController {
    private static final Logger log = LoggerFactory.getLogger(BeanExampleApplication.class);
    @Autowired
    private MessageService messageService;

    public String putMessage(String message) {
        log.debug("put Method starts: setMessage + getMessge");
        messageService.setMessage(" set messgae: " + message);
        return messageService.getMessage()+" this is get Messgae";
    }
    public void readMessage(String message){
        System.out.println(message);
    }

}
