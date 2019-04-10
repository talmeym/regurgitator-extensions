/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.Message;
import com.emarte.regurgitator.core.Parameters;
import com.emarte.regurgitator.core.RegurgitatorException;
import com.emarte.regurgitator.core.ValueSource;
import com.emarte.regurgitator.extensions.FreemarkerBuilder;
import org.junit.Test;

import java.io.IOException;

import static com.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static com.emarte.regurgitator.core.FileUtil.streamToString;
import static org.junit.Assert.assertEquals;

public class FreemarkerBuilderTest {
    @Test
    public void testFreemarkerBuilder() throws RegurgitatorException, IOException {
        FreemarkerBuilder toTest = new FreemarkerBuilder(new ValueSource(null, streamToString(getInputStreamForFile("classpath:/test-template-builder.xml"))), true);
        Message message = new Message(null);
        Parameters parameters = message.getParameters();
        parameters.setValue("name", "Miles");
        message.getContext("something-something").setValue("name", "Dave");
        String result = toTest.build(message);
        assertEquals("<doc>Hello Miles and Dave. Please to meet you.</doc>", result);
    }
}
