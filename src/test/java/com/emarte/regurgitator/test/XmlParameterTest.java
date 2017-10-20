/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.XmlParameter;
import com.emarte.regurgitator.extensions.XpathProcessor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static com.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static com.emarte.regurgitator.core.CoreTypes.*;
import static com.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static com.emarte.regurgitator.core.FileUtil.streamToString;
import static org.junit.Assert.assertEquals;

public class XmlParameterTest {
    private String xml;

    @Before
    public void setup() throws IOException {
        xml = streamToString(getInputStreamForFile("classpath:/xpath-test.xml"));
    }

    @Test
    public void testXmlDocument() throws RegurgitatorException {
        XmlParameter toTest1 = new XmlParameter("toTest1", new ParameterPrototype("names", LIST_OF_STRING, REPLACE), "parameters", new ValueSource(new ContextLocation("test:input"), null), new XpathProcessor("/doc/person/name", new HashMap<String, String>()), null);
        XmlParameter toTest2 = new XmlParameter("toTest2", new ParameterPrototype("ages", LIST_OF_NUMBER, REPLACE), "parameters", new ValueSource(new ContextLocation("test:input"), null), new XpathProcessor("/doc/person/age", new HashMap<String, String>()), null);

        Message message = new Message(null);
        message.getContext("test").setValue("input", STRING, xml);

        toTest1.execute(message);
        toTest2.execute(message);

        Parameters parameters = message.getParameters();

        assertEquals(2, parameters.size());
        assertEquals(Arrays.asList("miles", "dave"), parameters.getValue("names"));
        assertEquals(Arrays.asList(37L, 42L), parameters.getValue("ages"));
    }
}
