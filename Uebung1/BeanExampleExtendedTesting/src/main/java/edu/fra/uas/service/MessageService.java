package edu.fra.uas.service;

import org.springframework.stereotype.Component;

@Component
public class MessageService {

    private String message;
    private Integer counter;

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void increment(){
        this.counter++;
    }

}
