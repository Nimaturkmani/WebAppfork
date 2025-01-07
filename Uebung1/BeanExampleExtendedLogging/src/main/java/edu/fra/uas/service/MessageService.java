package edu.fra.uas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.fra.uas.BeanExampleApplication;

@Component
public class MessageService {

private static final Logger log = LoggerFactory.getLogger(BeanExampleApplication.class);
    
private String message;

    public String getMessage() {
        
    log.debug("getMessage: " + message);
    return message;
        
    }

    public void setMessage(String message) {
    log.debug("SetMessage: current message: " + this.message + "updated to: " + message);
        this.message = message;
    }

}
