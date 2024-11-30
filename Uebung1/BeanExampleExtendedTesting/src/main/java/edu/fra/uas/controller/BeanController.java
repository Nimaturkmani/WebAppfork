package edu.fra.uas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.service.MessageService;

@Component
public class BeanController {
    
    @Autowired
    private MessageService messageService;

    public String putMessage(String message) {
        messageService.setMessage(message);
        messageService.setCounter(1); //counter automatisch auf 1 gesetzt
        return messageService.getMessage();
    }
    
    public void outputMessage(){
        messageService.increment(); // conter wird auf eins erhört!
        System.out.println(messageService.getMessage()+ " " + messageService.getCounter().toString());
    }

}
