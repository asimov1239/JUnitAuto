package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCalculatorTest {

    @Test
    void PRYL_T2000_testeBasico(){
        var calculator = new SimpleCalculator();
        assertEquals(4, calculator.add(2,2));
    }

}