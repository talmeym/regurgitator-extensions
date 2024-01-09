/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.extensions.JsonPathProcessor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static uk.emarte.regurgitator.core.FileUtil.streamToString;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class JsonPathProcessorTest {
    private String json;

    @Before
    public void setup() throws IOException {
        json = streamToString(getInputStreamForFile("classpath:/jsonpath-test.json"));
    }

    @Test
    public void testThis() throws RegurgitatorException {
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.person[?(@.name=='miles')].age");
        assertEquals(singletonList(37), jsonpath.process(json, null));
    }

    @Test
    public void testThat() throws RegurgitatorException {
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.person[*].age");
        assertEquals(Arrays.asList(37, 42), jsonpath.process(json, null));
    }

    @Test
    public void testLinkedHashMap() throws IOException, RegurgitatorException {
        json = streamToString(getInputStreamForFile("classpath:/jsonpath-map-test.json"));
        JsonPathProcessor jsonpath = new JsonPathProcessor("$.object");
        assertEquals("{\"something\":\"miles\"}", jsonpath.process(json, null));
    }
}
