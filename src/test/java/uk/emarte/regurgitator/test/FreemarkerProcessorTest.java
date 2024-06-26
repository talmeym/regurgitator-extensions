/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import org.junit.Test;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.extensions.FreemarkerProcessor;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static uk.emarte.regurgitator.core.FileUtil.streamToString;
import static uk.emarte.regurgitator.core.StringType.stringify;

public class FreemarkerProcessorTest {
    @Test
    public void testProcessor() throws RegurgitatorException, IOException {
        FreemarkerProcessor toTest = new FreemarkerProcessor(streamToString(getInputStreamForFile("classpath:/test-template-processor.xml")));
        String result = stringify(toTest.process("Miles", new Message(null)));
        assertEquals("<doc>Hello Miles. Please to meet you.</doc>", result);
    }

    @Test
    public void testPassThrough() throws RegurgitatorException, IOException {
        FreemarkerProcessor toTest = new FreemarkerProcessor(streamToString(getInputStreamForFile("classpath:/test-template-processor.xml")));
        assertNull(toTest.process(null, new Message(null)));
    }
}