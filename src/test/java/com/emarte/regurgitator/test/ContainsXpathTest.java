package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.ContainsXpath;
import org.junit.Test;

import java.util.*;

import static com.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static com.emarte.regurgitator.core.CoreTypes.STRING;
import static junit.framework.Assert.*;

public class ContainsXpathTest {
	@Test
	public void testThis() throws RegurgitatorException {
		ContainsXpath toTest = new ContainsXpath(null);
		ParameterPrototype prototype = new ParameterPrototype("name", STRING, REPLACE);
		Parameter parameter = new Parameter(prototype, "<something><something>this</something></something>");
		assertTrue(toTest.evaluate(parameter, new Message(null), "/something/something", true));
		assertFalse(toTest.evaluate(parameter, new Message(null), "/something/something", false));
		assertFalse(toTest.evaluate(parameter, new Message(null), "/something/something/something", true));
		assertTrue(toTest.evaluate(parameter, new Message(null), "/something/something/something", false));
		assertFalse(toTest.evaluate(null, new Message(null), "/something/something", true));
		assertTrue(toTest.evaluate(null, new Message(null), "/something/something", false));
	}

	@Test
	public void testNamespaced_noneDefined() throws RegurgitatorException {
		ContainsXpath toTest = new ContainsXpath(null);
		ParameterPrototype prototype = new ParameterPrototype("name", STRING, REPLACE);
		Parameter parameter = new Parameter(prototype, "<something:something xmlns:something=\"http://something.com\"><something:something>this</something:something></something:something>");
		assertTrue(toTest.evaluate(parameter, new Message(null), "/*[local-name()='something' and namespace-uri()='http://something.com']/*[local-name()='something' and namespace-uri()='http://something.com']", true));
		assertFalse(toTest.evaluate(parameter, new Message(null), "/*[local-name()='something' and namespace-uri()='http://something.com']/*[local-name()='something' and namespace-uri()='http://something.com']", false));
		assertFalse(toTest.evaluate(parameter, new Message(null), "/*[local-name()='something' and namespace-uri()='http://something.com']/*[local-name()='something' and namespace-uri()='http://something.com']/*[local-name()='something' and namespace-uri()='http://something.com']", true));
		assertTrue(toTest.evaluate(parameter, new Message(null), "/*[local-name()='something' and namespace-uri()='http://something.com']/*[local-name()='something' and namespace-uri()='http://something.com']/*[local-name()='something' and namespace-uri()='http://something.com']", false));
	}

	@Test
	public void testNamespaced_nsDefined() throws RegurgitatorException {
		Map<String, String> namespaceUris = new HashMap<String, String>();
		namespaceUris.put("something", "http://something.com");
		ContainsXpath toTest = new ContainsXpath(namespaceUris);
		ParameterPrototype prototype = new ParameterPrototype("name", STRING, REPLACE);
		Parameter parameter = new Parameter(prototype, "<something:something xmlns:something=\"http://something.com\"><something:something>this</something:something></something:something>");
		assertTrue(toTest.evaluate(parameter, new Message(null), "/something:something/something:something", true));
		assertFalse(toTest.evaluate(parameter, new Message(null), "/something:something/something:something", false));
		assertFalse(toTest.evaluate(parameter, new Message(null), "/something:something/something:something/something:something", true));
		assertTrue(toTest.evaluate(parameter, new Message(null), "/something:something/something:something/something:something", false));
	}
}
