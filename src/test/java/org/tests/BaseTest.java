package org.tests;

import org.ExtentReportExtension;
import org.api.DemoApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testinfra.Logger;
import org.testinfra.apiutils.cilents.ApiStringClient;
import org.assertionutils.AssertionUtil;
import org.assertionutils.FailureStateTracker;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DemoApplication.class)
@ExtendWith(ExtentReportExtension.class)
public class BaseTest {

    @LocalServerPort
    int port;

    protected FailureStateTracker failureStateTracker;
    protected static AssertionUtil assertion;
    protected static ApiStringClient api;

    @BeforeAll
    public static void beforeAll() {
        assertion = new AssertionUtil();
    }

    @BeforeEach
    public void beforeEach() {
        Logger.get().newTestStep("Test Prep");
        failureStateTracker = new FailureStateTracker();
        api = new ApiStringClient(port);
    }

    @AfterEach
    public void afterEach() {
        Logger.get().newTestStep("Test Teardown");
        failureStateTracker.failSoftAsserts();
    }
}
