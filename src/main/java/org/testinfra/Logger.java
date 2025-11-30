package org.testinfra;

import com.aventstack.extentreports.ExtentTest;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;

public class Logger {

    private static Logger instance;
    private ExtentTest stepNode;
    private ExtentTest rootnode;

    private Logger(){}

    public static Logger get(){
        if(null == instance){
            instance = new Logger();
        }
        return instance;
    }

    public void init(@NotNull ExtentTest test){
        rootnode = test;
    }

    private void baseLog(String message){
        baseLog(message, getDefaultSeparator());
    }

    private void baseLog(String message, String separator){
        System.out.println(separator);
        System.out.printf("%s --- %s%n", DateTimeUtils.getCurrentTimestamp(), message);
        System.out.println(separator);
    }

    public void log(String message){
        baseLog(message);
        if(null == stepNode){
            stepNode = rootnode.createNode(message);
        }
        stepNode.info(message);
    }

    public void rootLog(String message){
        baseLog(message);
        rootnode.info(message);
    }

    public void fail(String message){
        String separator = String.join("", Collections.nCopies(5, "-FAIL"));
        baseLog("FAILURE: " + message, separator);
        stepNode.fail(message);
        rootnode.fail(message);
    }

    public void pass(){
        final String message = "Test Passed";
        stepNode.pass(message);
        rootnode.pass(message);
    }

    public void newTestStep(String message){
        baseLog("TEST STEP: " + message);
        stepNode = rootnode.createNode(message);
        rootLog(message);
    }

    private String getDefaultSeparator(){
        return String.join("", Collections.nCopies(12, "-"));
    }
}
