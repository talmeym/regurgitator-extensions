package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.XPathProcessor;
import org.junit.*;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class XPathProcessorTest {
	private String xml;

	@Before
	public void setup() throws IOException {
		xml = FileUtil.streamToString(FileUtil.getInputStreamForFile("classpath:/xpath-test.xml"));
	}

	@Test
	public void testThis() throws RegurgitatorException {
		XPathProcessor xPathProcessor = new XPathProcessor("/doc/person[name='miles']/age", new HashMap<String, String>());
		assertEquals("37", xPathProcessor.process(xml));
	}

	@Test
	public void testThat() throws RegurgitatorException {
		XPathProcessor xPathProcessor = new XPathProcessor("/doc/person/age", new HashMap<String, String>());
		assertEquals(Arrays.asList("37", "42"), xPathProcessor.process(xml));
	}
}
