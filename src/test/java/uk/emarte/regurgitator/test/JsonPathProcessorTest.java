/*
 * Copyright (C) 2017 martyn Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import org.junit.Before;
import org.junit.Test;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.extensions.JsonPathProcessor;

import java.io.IOException;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static uk.emarte.regurgitator.core.FileUtil.streamToString;

public class JsonPathProcessorTest {
    private String json;

    @Before
    public void setup() throws IOException {
        json = streamToString(getInputStreamForFile("classpath:/jsonpath-test.json"));
    }

    @Test
    public void testPersonAge() throws RegurgitatorException {
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.person[?(@.name=='martyn')].age");
        assertEquals(singletonList(37), jsonpath.process(json, null));
    }

    @Test
    public void testMartyn() throws RegurgitatorException {
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.person[?(@.name=='martyn')]");
        assertEquals(singletonList("{\"name\":\"martyn\",\"age\":37}"), jsonpath.process(json, null));
    }

    @Test
    public void testAllAges() throws RegurgitatorException {
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.person[*].age");
        assertEquals(asList(37, 42), jsonpath.process(json, null));
    }

    @Test
    public void testJsonObject() throws RegurgitatorException {
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.person[0]");
        assertEquals("{\"name\":\"martyn\",\"age\":37}", jsonpath.process(json, null));
    }

    @Test
    public void testJsonArray() throws RegurgitatorException {
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.person");
        assertEquals(asList("{\"name\":\"martyn\",\"age\":37}", "{\"name\":\"dave\",\"age\":42}"), jsonpath.process(json, null));
    }

    @Test
    public void testLinkedHashMap() throws IOException, RegurgitatorException {
        json = streamToString(getInputStreamForFile("classpath:/jsonpath-map-test.json"));
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.object");
        assertEquals("{\"something\":\"thing\"}", jsonpath.process(json, null));
    }

    @Test
    public void testNullIfNotFound() throws RegurgitatorException {
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.person[0].shoe_size");
        assertNull(jsonpath.process(json, null));
    }

    @Test
    public void testPassThrough() throws RegurgitatorException {
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.person[0]");
        assertNull(jsonpath.process(null, new Message(null)));
    }
}
