package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Selenium2LearnTest {

    @Test
    void testTitle(){
        Selenium2Learn learning = new Selenium2Learn();
        assertEquals("Nope Labs",learning.validateLogin());
    }


}