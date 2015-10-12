package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.VelocityBuilder;
import org.junit.Test;

import java.io.IOException;

import static com.emarte.regurgitator.core.CoreTypes.STRING;
import static org.junit.Assert.assertEquals;

public class VelocityBuilderTest {
	@Test
	public void testVelocityBuilder() throws RegurgitatorException, IOException {
		VelocityBuilder toTest = new VelocityBuilder(null, FileUtil.streamToString(FileUtil.getInputStreamForFile("classpath:/test-template.xml")));
		Message message = new Message(null);
		Parameters parameters = message.getParameters();
		parameters.setValue("name", STRING, "Miles");
		String result = toTest.build(message);
		assertEquals("<doc>Hello Miles. Please to meet you.</doc>", result);
	}
}
