package org.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testinfra.Logger;
import org.testinfra.assertionutils.AssertType;

import java.net.http.HttpResponse;

public class TextApiTests extends BaseTest{

    @Tag("Sanity")
    @DisplayName("Mirror Text API Test")
    @Test
    public void mirrorTextTest() throws Exception {
        Logger.get().rootLog("Testing text mirroring API.");

        Logger.get().newTestStep("Sending API Request");
        String inputText = "Testing text mirroring.";
        HttpResponse<String> response = api.sendTextMirrorRequest(inputText);

        Logger.get().newTestStep("Validations");
        successCodeAssert(response.statusCode());
        assertion.assertEquals(response.body(),
                "Your text: " + inputText, "The mirrored text should match the input text.",
                AssertType.HARD);
    }

    @Tag("Sanity")
    @DisplayName("Count Text API Test")
    @Test
    public void countTextTest() throws Exception {
        Logger.get().rootLog("Testing text count API.");

        Logger.get().newTestStep("Sending API Request");
        String inputText = "abcdefg";
        HttpResponse<Integer> response = api.sendTextCountRequest(inputText);

        Logger.get().newTestStep("Validations");
        successCodeAssert(response.statusCode());
        assertion.assertEquals(response.body(),
                7, "Text count expected to match input text length.",
                AssertType.HARD);
    }

    @Tag("Sanity")
    @DisplayName("Contains Text API Test")
    @Test
    public void containsTextTest() throws Exception {
        Logger.get().rootLog("Testing text contains API.");
        String inputText = "abcdefg";

        Logger.get().newTestStep("Checking for non-existing substring.");
        HttpResponse<Boolean> response = api.sendTextContainsRequest(inputText, "z");
        successCodeAssert(response.statusCode());
        assertion.assertEquals(response.body(),
                false, "Expected 'false' response.",
                AssertType.HARD);

        Logger.get().newTestStep("Checking for existing substring.");
        response = api.sendTextContainsRequest(inputText, "bc");
        successCodeAssert(response.statusCode());
        assertion.assertEquals(response.body(),
                true, "Expected 'true' response.",
                AssertType.HARD);
    }

    @Tag("Sanity")
    @DisplayName("Negative Contains Text API Test")
    @Test
    public void negativeContainsTextTest() throws Exception {
        Logger.get().log("Testing negative text contains API.");
        String inputText = "abcdefg";

        Logger.get().newTestStep("Sending API Request");
        HttpResponse<Boolean> response = api.sendTextContainsRequest(inputText);

        Logger.get().newTestStep("Validations");
        failureCodeAssert(response.statusCode());
    }

    @Tag("Fail Demo")
    @DisplayName("Failed Test Example with Soft Assertion")
    @Test
    public void failedTestExample() throws Exception{
        // Demonstrate test failure and soft assertion

        Logger.get().newTestStep("Demonstrating failed test with soft assertions.");
        String inputText = "abcdefg";
        HttpResponse<Boolean> response = api.sendTextContainsRequest(inputText);
        softSuccessCodeAssert(response.statusCode());

        Logger.get().newTestStep("Correct assertions after failure.");
        failureCodeAssert(response.statusCode());
    }

    private void httpCodeAssert(int actual, int expected, AssertType assertType){
        assertion.assertEquals(actual,
                expected, "HTTP Status should be " + expected,
                assertType);
    }

    private void failureCodeAssert(int actual){
        httpCodeAssert(actual, 400, AssertType.HARD);
    }

    private void successCodeAssert(int actual){
        httpCodeAssert(actual, 200, AssertType.HARD);
    }

    private void softSuccessCodeAssert(int actual){
        httpCodeAssert(actual, 200, AssertType.SOFT);
    }
}
