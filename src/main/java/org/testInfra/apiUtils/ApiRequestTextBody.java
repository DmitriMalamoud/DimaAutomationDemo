package org.testInfra.apiUtils;

public class ApiRequestTextBody {

    public String text;
    public String subtext;

    public ApiRequestTextBody(String text){
        this.text = text;
    }

    public ApiRequestTextBody(String text, String subtext){
        this.text = text;
        this.subtext = subtext;
    }
}
