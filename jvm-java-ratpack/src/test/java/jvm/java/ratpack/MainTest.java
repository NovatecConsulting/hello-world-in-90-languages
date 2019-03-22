package jvm.java.ratpack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ratpack.test.MainClassApplicationUnderTest;

public class MainTest {

    private MainClassApplicationUnderTest applicationUnderTest = new MainClassApplicationUnderTest(Main.class);

    @Test
    void unspecifiedQueryParamDefaultsToWorld () throws Exception {
        applicationUnderTest.test(
                testHttpClient ->  {
                    assertEquals("{\"message\":\"Hello World!\"}", testHttpClient.post("say-hello").getBody().getText());
                });
    }

    @Test
    void returnsSpecifiedQueryParam () throws Exception {
        applicationUnderTest.test(
                testHttpClient ->  {
                    assertEquals("{\"message\":\"Hello Test!\"}", testHttpClient.post("say-hello?name=Test").getBody().getText());
                });
    }
}