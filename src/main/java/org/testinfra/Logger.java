package org.testinfra;

import java.util.*;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

public class Logger {

    private static Logger instance;
    private Deque<String> logStack;

    private Logger(){}

    public static Logger get(){
        if(null == instance){
            instance = new Logger();
        }
        return instance;
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
        Allure.step(message);
    }

    public void fail(String message){
        String separator = String.join("", Collections.nCopies(5, "-FAIL"));
        baseLog("FAILURE: " + message, separator);
        Allure.step(message, Status.FAILED);
    }

    public void pass(){
        Allure.step("Test Passed", Status.PASSED);
    }

    public void newTestStep(String message){
        baseLog("TEST STEP: " + message);
        String uuid = UUID.randomUUID().toString();
        StepResult stepResult = new StepResult().setName(message);
        Allure.getLifecycle().startStep(uuid, stepResult);
        getLogStack().push(uuid);
    }

    public void exitTestStep() {
        if (getLogStack().isEmpty()) {
            return;
        }
        String uuid = getLogStack().pop();
        Allure.getLifecycle().stopStep(uuid);
    }

    public void exitAllTestSteps(){
        Iterator<String> iterator = getLogStack().descendingIterator();
        while(iterator.hasNext()){
            String uuid = iterator.next();
            Allure.getLifecycle().stopStep(uuid);
        }
    }

    private String getDefaultSeparator(){
        return String.join("", Collections.nCopies(12, "-"));
    }

    private Deque<String> getLogStack(){
        if(null == logStack){
            logStack = new ArrayDeque<>();
        }
        return logStack;
    }
}
