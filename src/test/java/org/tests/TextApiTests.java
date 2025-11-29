package org.tests;

import org.junit.jupiter.api.Test;
import org.testinfra.assertionutils.AssertType;

import java.net.http.HttpResponse;

public class TextApiTests extends BaseTest{

    @Test
    public void mirrorTextTest() throws Exception {
        String inputText = "Testing text mirroring.";
        HttpResponse<String> response = api.sendTextMirrorRequest(inputText);
        assertion.assertEquals(failureStateTracker, response.body(),
                "Your text: "+ inputText, "The mirrored text should match the input text.",
                AssertType.HARD);
    }

    @Test
    public void countTextTest() throws Exception {
        String inputText = "abcdefg";
        HttpResponse<Integer> response = api.sendTextCountRequest(inputText);
        assertion.assertEquals(failureStateTracker, response.body(),
                7, "Text count expected to match input text length.",
                AssertType.HARD);
    }
}
