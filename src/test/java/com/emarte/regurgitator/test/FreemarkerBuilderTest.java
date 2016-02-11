package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.FreemarkerBuilder;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FreemarkerBuilderTest {
	@Test
	public void testFreemarkerBuilder() throws RegurgitatorException, IOException {
		FreemarkerBuilder toTest = new FreemarkerBuilder(new ValueSource(null, FileUtil.streamToString(FileUtil.getInputStreamForFile("classpath:/test-template.xml"))), true);
		Message message = new Message(null);
		Parameters parameters = message.getParameters();
		parameters.setValue("name", "Miles");
		message.getContext("something-something").setValue("name", "Dave");
		String result = toTest.build(message);
		assertEquals("<doc>Hello Miles and Dave. Please to meet you.</doc>", result);
	}
}
