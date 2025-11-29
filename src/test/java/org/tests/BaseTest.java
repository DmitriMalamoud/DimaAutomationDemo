package org.tests;

import org.api.DemoApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testinfra.apiutils.drivers.ApiTextDriver;
import org.testinfra.assertionutils.AssertionUtil;
import org.testinfra.assertionutils.FailureStateTracker;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DemoApplication.class)
public class BaseTest {

    @LocalServerPort
    int port;

    protected FailureStateTracker failureStateTracker;
    protected static AssertionUtil assertion;
    protected static ApiTextDriver api;

    public static void init() {
        assertion = new AssertionUtil();
    }

    @BeforeAll
    public static void beforeAll() {
        init();
    }

    @BeforeEach
    public void beforeEach() {
        failureStateTracker = new FailureStateTracker();
        api = new ApiTextDriver(port);
    }

    @AfterEach
    public void afterEach() {
        failureStateTracker.failSoftAsserts();
    }
}
