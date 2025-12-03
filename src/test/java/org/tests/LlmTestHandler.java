package org.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testinfra.Logger;
import org.testinfra.llm.LlmGateway;
import org.testinfra.llm.LlmTestFailPromptBuilder;

import java.util.stream.Stream;

@Component
public class LlmTestHandler {

    @Autowired
    private LlmGateway llmGateway;

    public String getLlmResponse(String testClassName, String testMethodName, Throwable throwable) {
        LlmTestFailPromptBuilder promptBuilder = new LlmTestFailPromptBuilder();
        String prompt = promptBuilder
                .setClassName(testClassName)
                .setExceptionCause(throwable.getCause() != null ? throwable.getCause().getMessage() : "N/A")
                .setExceptionMessage(throwable.getMessage())
                .setExceptionStackTrace(reduceStackTraceToString(throwable))
                .setMethodName(testMethodName)
                .setStepsLog(Logger.get().getJournal())
                .setPromptType(LlmTestFailPromptBuilder.PromptType.DEFAULT)
                .build();
        return llmGateway.getLlmResponse(prompt);
    }

    private String reduceStackTraceToString(Throwable throwable) {
        return Stream.of(throwable.getStackTrace())
                .map(StackTraceElement::toString)
                .reduce(
                        (trace1, trace2) -> trace1 + " / " + trace2)
                .orElse("No stack trace available");
    }
}
