package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.XpathProcessor;
import org.junit.*;

import java.io.IOException;
import java.util.*;

import static com.emarte.regurgitator.core.FileUtil.getInputStreamForFile;
import static org.junit.Assert.assertEquals;

public class XpathProcessorTest {
	private String xml;

	@Before
	public void setup() throws IOException {
		xml = FileUtil.streamToString(getInputStreamForFile("classpath:/xpath-test.xml"));
	}

	@Test
	public void testThis() throws RegurgitatorException {
		XpathProcessor xpathProcessor = new XpathProcessor("/doc/person[name='miles']/age", null);
		assertEquals("37", xpathProcessor.process(xml, null));
	}

	@Test
	public void testThat() throws RegurgitatorException {
		XpathProcessor xpathProcessor = new XpathProcessor("/doc/person/age", null);
		assertEquals(Arrays.asList("37", "42"), xpathProcessor.process(xml, null));
	}
}
