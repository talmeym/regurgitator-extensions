package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.*;
import org.junit.*;

import java.io.IOException;
import java.util.Arrays;

import static com.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static com.emarte.regurgitator.core.CoreTypes.*;
import static org.junit.Assert.assertEquals;

public class JsonParameterTest {
	private String json;

	@Before
	public void setup() throws IOException {
		json = FileUtil.streamToString(FileUtil.getInputStreamForFile("classpath:/jsonpath-test.json"));
	}

	@Test
	public void testJsonDocument() throws RegurgitatorException {
		JsonParameter toTest1 = new JsonParameter("toTest1", new ParameterPrototype("names", LIST_OF_STRING, REPLACE), "parameters", new ContextLocation("test:input"), new JsonPathProcessor("$.person[*].name"), null);
		JsonParameter toTest2 = new JsonParameter("toTest2", new ParameterPrototype("ages", LIST_OF_NUMBER, REPLACE), "parameters", new ContextLocation("test:input"), new JsonPathProcessor("$.person[*].age"), null);

		Message message = new Message(null);
		message.getContext("test").setValue("input", STRING, json);

		toTest1.execute(message);
		toTest2.execute(message);

		Parameters parameters = message.getParameters();

		assertEquals(2, parameters.size());
		assertEquals(Arrays.asList("miles", "dave"), parameters.getValue("names"));
		assertEquals(Arrays.asList(37l, 42l), parameters.getValue("ages"));
	}
}
