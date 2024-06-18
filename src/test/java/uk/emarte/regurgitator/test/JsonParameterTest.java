/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import org.junit.Before;
import org.junit.Test;
import uk.emarte.regurgitator.core.*;
import uk.emarte.regurgitator.extensions.JsonParameter;
import uk.emarte.regurgitator.extensions.JsonPathProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static uk.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static uk.emarte.regurgitator.core.CoreTypes.*;
import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static uk.emarte.regurgitator.core.FileUtil.streamToString;

public class JsonParameterTest {
    private String json;

    @Before
    public void setup() throws IOException {
        json = streamToString(getInputStreamForFile("classpath:/jsonpath-test.json"));
    }

    @Test
    public void testJsonDocument() throws RegurgitatorException {
        JsonParameter toTest1 = new JsonParameter("toTest1", new ParameterPrototype("names", LIST_OF_STRING, REPLACE), "parameters", new ValueSource(new ContextLocation("test:input"), null), new JsonPathProcessor("$.person[*].name"), new ArrayList<>(), false);
        JsonParameter toTest2 = new JsonParameter("toTest2", new ParameterPrototype("ages", LIST_OF_NUMBER, REPLACE), "parameters", new ValueSource(new ContextLocation("test:input"), null), new JsonPathProcessor("$.person[*].age"), new ArrayList<>(), false);

        Message message = new Message(null);
        message.getContext("test").setValue("input", STRING, json);

        toTest1.execute(message);
        toTest2.execute(message);

        Parameters parameters = message.getParameters();

        assertEquals(2, parameters.size());
        assertEquals(Arrays.asList("martyn", "dave"), parameters.getValue("names"));
        assertEquals(Arrays.asList(37L, 42L), parameters.getValue("ages"));
    }

    @Test
    public void testOptional() throws RegurgitatorException {
        JsonParameter toTest = new JsonParameter("toTest", new ParameterPrototype("first_shoe_size", NUMBER, REPLACE), "parameters", new ValueSource(new ContextLocation("test:input"), null), new JsonPathProcessor("$.person[0].shoe_size"), new ArrayList<>(), true);

        Message message = new Message(null);
        message.getContext("test").setValue("input", STRING, json);

        toTest.execute(message);

        Parameters parameters = message.getParameters();
        assertEquals(0, parameters.size());
    }
}
