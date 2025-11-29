package org.testinfra.assertionutils;

import org.testinfra.Logger;

import java.util.List;

public class AssertionUtil {

    public void assertTrue(FailureStateTracker tracker, boolean condition, String message, AssertType assertType) {
        if (!condition) {
            Logger.fail(String.format("Condition not met (%s)", message));
            tracker.fail(message, assertType);
        }
    }

    public void assertFalse(FailureStateTracker tracker, boolean condition, String message, AssertType assertType) {
        assertTrue(tracker, !condition, message, assertType);
    }

    public void assertEquals(FailureStateTracker tracker, Object expected, Object actual, String message, AssertType assertType) {
        Logger.log("Asserting equal: expected [" + expected + "], actual [" + actual + "]");
        assertTrue(tracker, expected.equals(actual), message, assertType);
    }

    public void assertNotEquals(FailureStateTracker tracker, Object expected, Object actual, String message, AssertType assertType) {
        Logger.log("Asserting not equal: expected [" + expected + "], actual [" + actual + "]");
        assertTrue(tracker, !expected.equals(actual), message, assertType);
    }

    public void assertInList(FailureStateTracker tracker, List<Object> list, Object obj, String message, AssertType assertType) {
        Logger.log("Asserting list contains: " + obj);
        Logger.log("List: " + list);
        assertTrue(tracker, list.contains(obj), message, assertType);
    }

    public void assertNotInList(FailureStateTracker tracker, List<Object> list, Object obj, String message, AssertType assertType) {
        Logger.log("Asserting list does not contains: " + obj);
        Logger.log("List: " + list);
        assertTrue(tracker, !list.contains(obj), message, assertType);
    }
}
