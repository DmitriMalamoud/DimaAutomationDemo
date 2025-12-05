package org.demo.testinfra;

import java.util.*;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

public class Logger {

    private static Logger instance;
    private Deque<String> logStack;
    private List<String> journal;

    private static final String DEFAULT_SEPARATOR = "-".repeat(15);

    private Logger(){}

    public static Logger get(){
        if(null == instance){
            instance = new Logger();
        }
        return instance;
    }

    private void baseLog(String message){
        baseLog(message, DEFAULT_SEPARATOR);
    }

    private void baseLog(String message, String separator){
        final String processedMessage = addTimestampToMessage(message);
        System.out.println(separator);
        System.out.printf(processedMessage, message);
        System.out.println();
        System.out.println(separator);

        getJournalObject().add(processedMessage);
    }

    public void log(String message){
        baseLog(message);
        Allure.step(addTimestampToMessage(message));
    }

    public void fail(String message){
        String separator = "-FAIL".repeat(5);
        baseLog("FAILURE: " + message, separator);
        Allure.step(addTimestampToMessage(message), Status.FAILED);
    }

    public void warning(String message){
        String separator = "-WARNING".repeat(5);
        baseLog("WARNING: " + message, separator);
        Allure.step(addTimestampToMessage(message), Status.BROKEN);
    }

    public void pass(){
        Allure.step("Test Passed", Status.PASSED);
    }

    public void newTestStep(String message){
        baseLog("TEST STEP: " + message);
        String uuid = UUID.randomUUID().toString();
        StepResult stepResult = new StepResult().setName(addTimestampToMessage(message));
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

    private Deque<String> getLogStack(){
        if(null == logStack){
            logStack = new ArrayDeque<>();
        }
        return logStack;
    }

    private List<String> getJournalObject() {
        if(null == journal){
            journal = new ArrayList<>();
        }
        return journal;
    }

    private String addTimestampToMessage(String message){
        return "%s --- %s".formatted(DateTimeUtils.getCurrentTimestamp(), message);
    }

    //returns a copy of the journal
    public List<String> getJournal() {
        return new ArrayList<>(getJournalObject());
    }
}
