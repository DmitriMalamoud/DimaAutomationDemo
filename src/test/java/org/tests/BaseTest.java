package org.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testinfra.apiutils.drivers.ApiTextDriver;
import org.testinfra.assertionutils.AssertionUtil;
import org.testinfra.assertionutils.FailureStateTracker;

public class BaseTest {

    protected FailureStateTracker failureStateTracker;
    protected static AssertionUtil assertion;
    protected static ApiTextDriver api;

    public static void init() {
        assertion = new AssertionUtil();
        api = new ApiTextDriver();
    }

    @BeforeAll
    public static void beforeAll() {
        init();
    }

    @BeforeEach
    public void beforeEach() {
        failureStateTracker = new FailureStateTracker();
    }

    @AfterEach
    public void afterEach() {
        failureStateTracker.failSoftAsserts();
    }
}
