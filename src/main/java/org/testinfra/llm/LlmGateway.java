package org.testinfra.llm;

import org.springframework.stereotype.Component;

@Component
public class LlmGateway {

    public String getLlmResponse(String prompt) {
        // todo: implement actual LLM integration
        return "LLM mock response based on prompt: \n\n" + prompt;
    }
}
