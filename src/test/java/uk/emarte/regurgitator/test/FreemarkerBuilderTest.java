/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import org.junit.Test;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.Parameters;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.core.ValueSource;
import uk.emarte.regurgitator.extensions.FreemarkerBuilder;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static uk.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static uk.emarte.regurgitator.core.FileUtil.streamToString;

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
