/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import org.junit.Before;
import org.junit.Test;
import uk.emarte.regurgitator.core.*;
import uk.emarte.regurgitator.extensions.XmlParameter;
import uk.emarte.regurgitator.extensions.XpathProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static uk.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static uk.emarte.regurgitator.core.CoreTypes.*;
import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static uk.emarte.regurgitator.core.FileUtil.streamToString;

public class XmlParameterTest {
    private String xml;

    @Before
    public void setup() throws IOException {
        xml = streamToString(getInputStreamForFile("classpath:/xpath-test.xml"));
    }

    @Test
    public void testXmlDocument() throws RegurgitatorException {
        XmlParameter toTest1 = new XmlParameter("toTest1", new ParameterPrototype("names", LIST_OF_STRING, REPLACE), "parameters", new ValueSource(new ContextLocation("test:input"), null), new XpathProcessor("/doc/person/name", new HashMap<>()), new ArrayList<>());
        XmlParameter toTest2 = new XmlParameter("toTest2", new ParameterPrototype("ages", LIST_OF_NUMBER, REPLACE), "parameters", new ValueSource(new ContextLocation("test:input"), null), new XpathProcessor("/doc/person/age", new HashMap<>()), new ArrayList<>());

        Message message = new Message(null);
        message.getContext("test").setValue("input", STRING, xml);

        toTest1.execute(message);
        toTest2.execute(message);

        Parameters parameters = message.getParameters();

        assertEquals(2, parameters.size());
        assertEquals(Arrays.asList("martyn", "dave"), parameters.getValue("names"));
        assertEquals(Arrays.asList(37L, 42L), parameters.getValue("ages"));
    }
}
