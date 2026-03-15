package com.restassured.tests;

import com.restassured.config.BaseConfig;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    
    @BeforeClass
    public void setup() {
        BaseConfig.setupRestAssured();
    }
}

