package org.tests;

import io.qameta.allure.junit5.AllureJunit5;
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
@ActiveProfiles("local")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=8085",
        classes = SpringApplication.class)
@ExtendWith(AllureJunit5.class)
@ExtendWith(TestListener.class)
public abstract class BaseTest {
    protected TestFailureStateHandler failureHandler;
    protected static AssertionUtil assertion;

    @Autowired
    protected ApiStringClient api;
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
        Logger.get().exitAllTestSteps();
        Logger.get().newTestStep("Test Teardown");
        try {
            // More teardown actions can be added here
            failureHandler.failSoftAsserts();
        }
        finally {
            Logger.get().exitTestStep();
        }
    }
}
