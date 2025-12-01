package org.assertionutils;

import org.testinfra.Logger;

import java.util.List;

public class AssertionUtil {

    private TestFailureStateHandler testFailureStateHandler;

    public void setFailureStateTracker(TestFailureStateHandler testFailureStateHandler) {
        this.testFailureStateHandler = testFailureStateHandler;
    }

    public void assertTrue(boolean condition, String message, AssertType assertType) {
        if (!condition) {
            Logger.get().fail(String.format("Assertion failed: %s", message));
            testFailureStateHandler.fail(message, assertType);
        }
        else {
            Logger.get().log("Assertion passed: " + message);
        }
    }

    public void assertFalse(boolean condition, String message, AssertType assertType) {
        assertTrue(!condition, message, assertType);
    }

    public void assertEquals(Object actual, Object expected, String message, AssertType assertType) {
        Logger.get().log("Asserting equal: expected [" + expected + "], actual [" + actual + "]");
        assertTrue(expected.equals(actual), message, assertType);
    }

    public void assertNotEquals(Object actual, Object expected, String message, AssertType assertType) {
        Logger.get().log("Asserting not equal: expected [" + expected + "], actual [" + actual + "]");
        assertTrue(!expected.equals(actual), message, assertType);
    }

    public void assertInList(List<Object> list, Object obj, String message, AssertType assertType) {
        Logger.get().log("Asserting list contains: " + obj);
        Logger.get().log("List: " + list);
        assertTrue(list.contains(obj), message, assertType);
    }

    public void assertNotInList(List<Object> list, Object obj, String message, AssertType assertType) {
        Logger.get().log("Asserting list does not contains: " + obj);
        Logger.get().log("List: " + list);
        assertTrue(!list.contains(obj), message, assertType);
    }
}
