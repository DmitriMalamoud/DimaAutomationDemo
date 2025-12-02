package org.testinfra.apiserver;

import jakarta.validation.constraints.NotNull;

public class TextRequest {
    @NotNull(message = "Text must not be null")
    private String text;
    private String subtext;

    public TextRequest(){}


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }
}
