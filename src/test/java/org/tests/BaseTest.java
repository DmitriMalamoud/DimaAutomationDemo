package org.tests;

import org.ExtentReportExtension;
import org.testinfra.SpringApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testinfra.Logger;
import org.testinfra.apiutils.clients.ApiStringClient;
import org.testinfra.assertionutils.AssertionUtil;
import org.testinfra.assertionutils.TestFailureStateHandler;
import org.testinfra.config.TestEnvConfig;

// Use active profile for local tests runs
//@ActiveProfiles("local")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=8085",
        classes = SpringApplication.class)
@ExtendWith(ExtentReportExtension.class)
public class BaseTest {
    protected TestFailureStateHandler failureHandler;
    protected static AssertionUtil assertion;

    @Autowired
    protected ApiStringClient api;
    int port;

    @Autowired
    private TestEnvConfig env;

    @BeforeAll
    public static void beforeAll() {
        assertion = new AssertionUtil();
    }

    @BeforeEach
    public void beforeEach() {
        Logger.get().newTestStep("Test Prep");
        failureHandler = new TestFailureStateHandler();
        assertion.setFailureStateTracker(failureHandler);
    }

    @AfterEach
    public void afterEach() {
        Logger.get().newTestStep("Test Teardown");
        failureHandler.failSoftAsserts();
    }
}
