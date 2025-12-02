package org.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testinfra.Logger;
import org.testinfra.assertionutils.AssertType;

import java.net.http.HttpResponse;

public class TextApiTests extends BaseTest{

    @Tag("sanity")
    @DisplayName("Mirror Text API Test")
    @Test
    public void mirrorTextTest() throws Exception {
        Logger.get().log("Testing text mirroring API.");

        Logger.get().newTestStep("Sending API Request");
        String inputText = "Testing text mirroring.";
        HttpResponse<String> response = api.sendTextMirrorRequest(inputText);
        Logger.get().exitTestStep();

        Logger.get().newTestStep("Validations");
        successCodeAssert(response.statusCode());
        assertion.assertEquals(response.body(),
                "Your text: " + inputText, "The mirrored text should match the input text.",
                AssertType.HARD);
        Logger.get().exitTestStep();
    }

    @Tag("sanity")
    @DisplayName("Count Text API Test")
    @Test
    public void countTextTest() throws Exception {
        Logger.get().log("Testing text count API.");

        Logger.get().newTestStep("Sending API Request");
        String inputText = "abcdefg";
        HttpResponse<Integer> response = api.sendTextCountRequest(inputText);
        Logger.get().exitTestStep();

        Logger.get().newTestStep("Validations");
        successCodeAssert(response.statusCode());
        assertion.assertEquals(response.body(),
                7, "Text count expected to match input text length.",
                AssertType.HARD);
        Logger.get().exitTestStep();
    }

    @Tag("sanity")
    @DisplayName("Contains Text API Test")
    @Test
    public void containsTextTest() throws Exception {
        Logger.get().log("Testing text contains API.");
        String inputText = "abcdefg";

        Logger.get().newTestStep("Checking for non-existing substring.");

        Logger.get().newTestStep("Sending API Request");
        HttpResponse<Boolean> response = api.sendTextContainsRequest(inputText, "z");
        Logger.get().exitTestStep();

        Logger.get().newTestStep("Validations");
        successCodeAssert(response.statusCode());
        assertion.assertEquals(response.body(),
                false, "Expected 'false' response.",
                AssertType.HARD);
        Logger.get().exitTestStep();

        Logger.get().exitTestStep();

        Logger.get().newTestStep("Checking for existing substring.");

        Logger.get().newTestStep("Sending API Request");
        response = api.sendTextContainsRequest(inputText, "bc");
        Logger.get().exitTestStep();

        Logger.get().newTestStep("Validations");
        successCodeAssert(response.statusCode());
        assertion.assertEquals(response.body(),
                true, "Expected 'true' response.",
                AssertType.HARD);
        Logger.get().exitTestStep();

        Logger.get().exitTestStep();
    }

    @Tag("sanity")
    @DisplayName("Negative Contains Text API Test")
    @Test
    public void negativeContainsTextTest() throws Exception {
        Logger.get().log("Testing negative text contains API.");
        String inputText = "abcdefg";

        Logger.get().newTestStep("Sending API Request");
        HttpResponse<Boolean> response = api.sendTextContainsRequest(inputText);
        Logger.get().exitTestStep();

        Logger.get().newTestStep("Validations");
        failureCodeAssert(response.statusCode());
        Logger.get().exitTestStep();
    }

    @Tag("fail_demo")
    @DisplayName("Failed Test Example with Soft Assertion")
    @Test
    public void failedTestExample() throws Exception{
        // Demonstrate test failure and soft assertion

        Logger.get().newTestStep("Demonstrating failed test with soft assertions.");
        String inputText = "abcdefg";

        Logger.get().newTestStep("Sending API Request");
        HttpResponse<Boolean> response = api.sendTextContainsRequest(inputText);
        Logger.get().exitTestStep();

        Logger.get().newTestStep("Validation (with soft failure)");
        softSuccessCodeAssert(response.statusCode());
        Logger.get().exitTestStep();
        Logger.get().exitTestStep();

        Logger.get().newTestStep("Correct assertions after failure.");
        failureCodeAssert(response.statusCode());
        Logger.get().exitTestStep();
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
