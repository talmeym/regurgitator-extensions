/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.extensions.VelocityProcessor;
import org.junit.Test;

import java.io.IOException;

import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static uk.emarte.regurgitator.core.FileUtil.streamToString;
import static uk.emarte.regurgitator.core.StringType.stringify;
import static org.junit.Assert.assertEquals;

public class VelocityProcessorTest {
    @Test
    public void testVelocityProcessor() throws RegurgitatorException, IOException {
        VelocityProcessor toTest = new VelocityProcessor(streamToString(getInputStreamForFile("classpath:/test-template-processor.xml")));
        String result = stringify(toTest.process("Miles", new Message(null)));
        assertEquals("<doc>Hello Miles. Please to meet you.</doc>", result);
    }
}