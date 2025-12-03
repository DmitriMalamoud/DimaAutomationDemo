package org.testinfra.llm;

public class LlmGateway {

    private static LlmGateway instance;

    private LlmGateway(){
    }

    public static LlmGateway get(){
        if(instance == null){
            instance = new LlmGateway();
        }
        return instance;
    }

    public String getLlmResponse(String prompt) {
        // todo: implement actual LLM integration
        return "LLM mock response based on prompt: \n\n" + prompt;
    }
}
