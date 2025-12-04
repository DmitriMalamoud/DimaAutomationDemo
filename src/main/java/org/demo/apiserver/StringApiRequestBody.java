package org.demo.apiserver;

import jakarta.validation.constraints.NotNull;

public class StringApiRequestBody {
    @NotNull(message = "Text must not be null")
    private String text;
    private String subtext;

    public StringApiRequestBody(){}


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
