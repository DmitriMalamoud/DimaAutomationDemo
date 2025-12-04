package org.demo.testinfra.llm;

public class OllamaApiRequestPojo {
    private String model;
    private String prompt;
    private Boolean stream;
    private int num_predict;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public int getNum_predict() {
        return num_predict;
    }

    public void setNum_predict(int num_predict) {
        this.num_predict = num_predict;
    }
}
