package org.tests;

import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    public static void initTest() {
        // Initializations go here
    }

    @BeforeAll
    public static void beforeAll() {
        initTest();
    }
}
