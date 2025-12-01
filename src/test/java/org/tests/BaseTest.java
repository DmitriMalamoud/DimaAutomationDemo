package org.tests;

import org.ExtentReportExtension;
import org.api.DemoApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testinfra.Logger;
import org.testinfra.apiutils.cilents.ApiStringClient;
import org.testinfra.assertionutils.AssertionUtil;
import org.testinfra.assertionutils.TestFailureStateHandler;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DemoApplication.class)
@ExtendWith(ExtentReportExtension.class)
public class BaseTest {
    @LocalServerPort
    int port;

    protected TestFailureStateHandler failureHandler;
    protected static AssertionUtil assertion;
    protected static ApiStringClient api;

    @BeforeAll
    public static void beforeAll() {
        assertion = new AssertionUtil();
    }

    @BeforeEach
    public void beforeEach() {
        Logger.get().newTestStep("Test Prep");
        failureHandler = new TestFailureStateHandler();
        assertion.setFailureStateTracker(failureHandler);
        api = new ApiStringClient(port);
    }

    @AfterEach
    public void afterEach() {
        Logger.get().newTestStep("Test Teardown");
        failureHandler.failSoftAsserts();
    }
}
