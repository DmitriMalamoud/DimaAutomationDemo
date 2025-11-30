package org.assertionutils;

import org.testinfra.Logger;

import java.util.LinkedList;
import java.util.List;

public class FailureStateTracker {
    private List<String> failures;

    public void fail(String message, AssertType assertType) {
        switch (assertType){
            case HARD -> throw new AssertionError(message);
            case SOFT -> {
                if(null == failures){
                    failures = new LinkedList<>();
                }
                failures.add(message);
            }
        }
    }

    public void failSoftAsserts() {
        if(failures != null && !failures.isEmpty()){
            String failuresStr = failures.stream().reduce((failure1, failure2) ->
                    failure1 + "\n" + failure2).get();
            Logger.get().fail("Test failed: " + failuresStr);
            throw new AssertionError(failuresStr);
        }
        else{
            Logger.get().pass();
        }
    }
}
