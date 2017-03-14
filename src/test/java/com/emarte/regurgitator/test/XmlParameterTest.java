package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.*;
import org.junit.*;

import java.io.IOException;
import java.util.*;

import static com.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static com.emarte.regurgitator.core.CoreTypes.*;
import static org.junit.Assert.assertEquals;

public class XmlParameterTest {
	private String xml;

	@Before
	public void setup() throws IOException {
		xml = FileUtil.streamToString(FileUtil.getInputStreamForFile("classpath:/xpath-test.xml"));
	}

	@Test
	public void testXmlDocument() throws RegurgitatorException {
		XmlParameter toTest1 = new XmlParameter("toTest1", new ParameterPrototype("names", LIST_OF_STRING, REPLACE), "parameters", new ValueSource(new ContextLocation("test:input"), null), new XPathProcessor("/doc/person/name", new HashMap<String, String>()), null);
		XmlParameter toTest2 = new XmlParameter("toTest2", new ParameterPrototype("ages", LIST_OF_NUMBER, REPLACE), "parameters", new ValueSource(new ContextLocation("test:input"), null), new XPathProcessor("/doc/person/age", new HashMap<String, String>()), null);

		Message message = new Message(null);
		message.getContext("test").setValue("input", STRING, xml);

		toTest1.execute(message);
		toTest2.execute(message);

		Parameters parameters = message.getParameters();

		assertEquals(2, parameters.size());
		assertEquals(Arrays.asList("miles", "dave"), parameters.getValue("names"));
		assertEquals(Arrays.asList(37l, 42l), parameters.getValue("ages"));
	}
}
