package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.XPathProcessor;
import org.junit.*;

import java.io.IOException;
import java.util.*;

import static com.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static org.junit.Assert.assertEquals;

public class XPathProcessorTest {
	private String xml;

	@Before
	public void setup() throws IOException {
		xml = FileUtil.streamToString(getInputStreamForFile("classpath:/xpath-test.xml"));
	}

	@Test
	public void testThis() throws RegurgitatorException {
		XPathProcessor XPathProcessor = new XPathProcessor("/doc/person[name='miles']/age", null);
		assertEquals("37", XPathProcessor.process(xml, null));
	}

	@Test
	public void testThat() throws RegurgitatorException {
		XPathProcessor XPathProcessor = new XPathProcessor("/doc/person/age", null);
		assertEquals(Arrays.asList("37", "42"), XPathProcessor.process(xml, null));
	}
}
