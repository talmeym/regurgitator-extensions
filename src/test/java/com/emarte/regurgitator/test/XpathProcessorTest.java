/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.RegurgitatorException;
import com.emarte.regurgitator.extensions.XpathProcessor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static com.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static com.emarte.regurgitator.core.FileUtil.streamToString;
import static org.junit.Assert.assertEquals;

public class XpathProcessorTest {
    private String xml;

    @Before
    public void setup() throws IOException {
        xml = streamToString(getInputStreamForFile("classpath:/xpath-test.xml"));
    }

    @Test
    public void testThis() throws RegurgitatorException {
        XpathProcessor XpathProcessor = new XpathProcessor("/doc/person[name='miles']/age", null);
        assertEquals("37", XpathProcessor.process(xml, null));
    }

    @Test
    public void testThat() throws RegurgitatorException {
        XpathProcessor XpathProcessor = new XpathProcessor("/doc/person/age", null);
        assertEquals(Arrays.asList("37", "42"), XpathProcessor.process(xml, null));
    }
}
