package org.tests;

import org.ExtentReportExtension;
import org.api.DemoApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testinfra.Logger;
import org.testinfra.apiutils.cilents.ApiStringClient;
import org.assertionutils.AssertionUtil;
import org.assertionutils.TestFailureStateHandler;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DemoApplication.class)
@ExtendWith(ExtentReportExtension.class)
public class BaseTest {
    @LocalServerPort
    int port;

    private volatile boolean testThrewException;
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
        testThrewException = false;
    }

    @AfterEach
    public void afterEach() {
        Logger.get().newTestStep("Test Teardown");
        failureHandler.failSoftAsserts();
        if(!testThrewException){
            Logger.get().pass();
        }
    }

    @RegisterExtension
    TestWatcher testWatcher = new TestWatcher() {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            testThrewException = true;
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            testThrewException = false;
        }
    };
}
