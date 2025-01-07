package edu.fra.uas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HelloWorldController {

    @RequestMapping(value = "helloMax", method=RequestMethod.GET) // Value gibt an was nach / kommt -> localhost:8080/helloMax
    @ResponseBody
    public String getHelloWorld(){
        return "<h1> hello world i am here </h1>";
    }
    }
