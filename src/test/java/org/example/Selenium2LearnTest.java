package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class Selenium2LearnTest {

    ChromeDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/v1/");
    }


    @Test
    void testTitle(){
        Selenium2Learn learning = new Selenium2Learn();
        assertEquals("Swag Labs",learning.validateLogin());
    }

    @Test
    void testURL(){
        assertEquals("https://www.saucedemo.com/v1/", driver.getCurrentUrl());
    }

//    @Test
//    void testLogin(){
//        driver.findElement(By.id("user-name")).sendKeys("standard_user");
//        driver.findElement(By.id("password")).sendKeys("secret_sauce");
//    }
//
}