package org.tests;

import org.junit.jupiter.api.Test;
import org.testinfra.Logger;
import org.testinfra.assertionutils.AssertType;

import java.net.http.HttpResponse;

public class TextApiTests extends BaseTest{

    @Test
    public void mirrorTextTest() throws Exception {
        Logger.log("Testing text mirroring API.");
        String inputText = "Testing text mirroring.";
        HttpResponse<String> response = api.sendTextMirrorRequest(inputText);
        successCodeAssert(response.statusCode());
        assertion.assertEquals(failureStateTracker, response.body(),
                "Your text: " + inputText, "The mirrored text should match the input text.",
                AssertType.HARD);
    }

    @Test
    public void countTextTest() throws Exception {
        Logger.log("Testing text count API.");
        String inputText = "abcdefg";
        HttpResponse<Integer> response = api.sendTextCountRequest(inputText);
        successCodeAssert(response.statusCode());
        assertion.assertEquals(failureStateTracker, response.body(),
                7, "Text count expected to match input text length.",
                AssertType.HARD);
    }

    private void successCodeAssert(int actual){
        assertion.assertEquals(failureStateTracker, actual,
                200, "HTTP Status should be 200.",
                AssertType.HARD);
    }
}
