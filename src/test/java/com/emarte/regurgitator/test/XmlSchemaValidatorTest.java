package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.XmlSchemaValidator;
import org.junit.*;

import static junit.framework.Assert.assertEquals;

public class XmlSchemaValidatorTest {
	private XmlSchemaValidator totest;

	@Before
	public void setUp() throws RegurgitatorException {
		totest = new XmlSchemaValidator("classpath:/xml-schema-test.xsd");
	}

	@Test
	public void testSuccesfulValidation() throws RegurgitatorException {
		String value = "<doc>A String</doc>";
		assertEquals(value, totest.process(value, new Message(null)));
	}

	@Test(expected = RegurgitatorException.class)
	public void testUnsuccesfulValidation() throws RegurgitatorException {
		totest.process("<dog>A String</dog>", new Message(null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingSchema() throws RegurgitatorException {
		totest = new XmlSchemaValidator("classpath:/doesntexist.xsd");
	}
}
