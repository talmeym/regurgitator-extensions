/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import org.junit.Before;
import org.junit.Test;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.extensions.XpathProcessor;

import java.io.IOException;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static uk.emarte.regurgitator.core.FileUtil.streamToString;

public class XpathProcessorTest {
    private String xml;

    @Before
    public void setup() throws IOException {
        xml = streamToString(getInputStreamForFile("classpath:/xpath-test.xml"));
    }

    @Test
    public void testOneAge() throws RegurgitatorException {
        XpathProcessor xpathProcessor = new XpathProcessor("/doc/person[name='martyn']/age", null);
        assertEquals(singletonList("37"), xpathProcessor.process(xml, null));
    }

    @Test
    public void testAllAges() throws RegurgitatorException {
        XpathProcessor xpathProcessor = new XpathProcessor("/doc/person/age", null);
        assertEquals(asList("37", "42"), xpathProcessor.process(xml, null));
    }

    @Test
    public void testEmptyListIfNotFound() throws RegurgitatorException {
        XpathProcessor xpathProcessor = new XpathProcessor("/doc/a_person/show_size", null);
        assertEquals(emptyList(), xpathProcessor.process(xml, null));
    }

    @Test
    public void testPassThrough() throws RegurgitatorException {
        XpathProcessor xpathProcessor = new XpathProcessor("/doc/a_person/show_size", null);
        assertNull(xpathProcessor.process(null, new Message(null)));
    }
}
