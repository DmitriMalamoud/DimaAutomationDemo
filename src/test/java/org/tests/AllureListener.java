package org.tests;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
public class AllureListener implements TestExecutionExceptionHandler {

    @Autowired
    private LlmTestHandler reporter;

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        Allure.addAttachment("LLM  Test Failure Analysis",
                reporter.getLlmResponse(
                        extensionContext.getRequiredTestClass().getName(),
                        extensionContext.getRequiredTestMethod().getName(),
                        throwable));
        throw throwable;
    }
}
