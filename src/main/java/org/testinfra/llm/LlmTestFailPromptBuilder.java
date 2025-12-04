package org.testinfra.llm;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Component;
import org.testinfra.config.LlmConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class LlmTestFailPromptBuilder {
    private LlmConfig config; //todo

    private List<String> stepsLog;
    private String methodName;
    private String className;
    private String exceptionMessage;
    private String exceptionCause;
    private String exceptionStackTrace;
    private String promptTemplatePath;
    private PromptType promptType;

    public String build(){
        switch(promptType){
            case DEFAULT -> promptTemplatePath = config.getPrompt().getDefaultPath();
            case CUSTOM -> {
                if(promptTemplatePath == null || promptTemplatePath.isEmpty()){
                    throw new IllegalArgumentException("Custom prompt template path is not set.");
                }
            }
        }
        String promptTemplate;
        try{
            promptTemplate = Files.readString(Path.of(promptTemplatePath));
        }
        catch (IOException e){
            throw new RuntimeException("Failed to read prompt template from path: " + promptTemplatePath, e);
        }

        Map<String, Object> values = Map.of(
                "stepsLog", stepsLog,
                "exceptionCause", exceptionCause,
                "exceptionMessage", exceptionMessage,
                "exceptionStackTrace", exceptionStackTrace,
                "methodName", methodName,
                "className", className
        );

        return new StringSubstitutor(values, "${", "}").replace(promptTemplate);
    }
    public enum PromptType{
        DEFAULT,
        CUSTOM
    }

    public LlmTestFailPromptBuilder setStepsLog(List<String> stepsLog) {
        this.stepsLog = stepsLog;
        return this;
    }

    public LlmTestFailPromptBuilder setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public LlmTestFailPromptBuilder setClassName(String className) {
        this.className = className;
        return this;
    }

    public LlmTestFailPromptBuilder setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public LlmTestFailPromptBuilder setExceptionCause(String exceptionCause) {
        this.exceptionCause = exceptionCause;
        return this;
    }

    public LlmTestFailPromptBuilder setExceptionStackTrace(String exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
        return this;
    }

    public LlmTestFailPromptBuilder setPromptType(PromptType promptType) {
        this.promptType = promptType;
        return this;
    }

    public LlmTestFailPromptBuilder setPromptTemplatePath(String promptPath) {
        this.promptTemplatePath = promptPath;
        return this;
    }
}
