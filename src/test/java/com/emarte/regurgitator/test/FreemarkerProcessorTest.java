/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.FreemarkerProcessor;
import org.junit.Test;

import java.io.IOException;

import static com.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static com.emarte.regurgitator.core.FileUtil.streamToString;
import static com.emarte.regurgitator.core.StringType.stringify;
import static org.junit.Assert.assertEquals;

public class FreemarkerProcessorTest {
    @Test
    public void testFreemarkerBuilder() throws RegurgitatorException, IOException {
        FreemarkerProcessor toTest = new FreemarkerProcessor(streamToString(getInputStreamForFile("classpath:/test-template-processor.xml")));
        String result = stringify(toTest.process("Miles", new Message(null)));
        assertEquals("<doc>Hello Miles. Please to meet you.</doc>", result);
    }
}