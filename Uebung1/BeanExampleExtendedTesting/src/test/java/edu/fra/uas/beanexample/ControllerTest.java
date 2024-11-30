package edu.fra.uas.beanexample;

import static org.assertj.core.api.Assertions.assertThat; //Zum testen nötig

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.fra.uas.controller.BeanController;

@SpringBootTest
public class ControllerTest {
    
    @Autowired
    private BeanController beanController;

    @Test
    void testController() {
    assertThat(beanController.putMessage("Das ist ein Test 2")).isEqualTo("Das ist ein Test 2"); // bei putmessage methode das This message löschen
    beanController.outputMessage();
    }
}
