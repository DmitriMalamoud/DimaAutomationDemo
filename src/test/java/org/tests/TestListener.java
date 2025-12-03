package org.tests;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class TestListener implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        //TODO: should be injected
        LlmTestHandler llm = new LlmTestHandler();

        Allure.addAttachment("LLM  Test Failure Analysis",
                llm.getLlmResponse(
                        extensionContext.getRequiredTestClass().getName(),
                        extensionContext.getRequiredTestMethod().getName(),
                        throwable));
    }
}
