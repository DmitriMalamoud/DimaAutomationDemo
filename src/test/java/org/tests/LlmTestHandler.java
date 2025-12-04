package org.tests;

import org.springframework.stereotype.Component;
import org.testinfra.Logger;
import org.testinfra.llm.LlmGateway;
import org.testinfra.llm.LlmTestFailPromptBuilder;

import java.util.stream.Stream;

@Component
public class LlmTestHandler {

    public String getLlmResponse(String testClassName, String testMethodName, Throwable throwable) {
        if(!Boolean.getBoolean(System.getProperty("llm"))){
            return "LLM integration is disabled.";
        }

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
        return LlmGateway.get().getLlmResponse(prompt);
    }

    private String reduceStackTraceToString(Throwable throwable) {
        return Stream.of(throwable.getStackTrace())
                .map(StackTraceElement::toString)
                .reduce(
                        (trace1, trace2) -> trace1 + " / " + trace2)
                .orElse("No stack trace available");
    }
}
