/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import org.junit.Test;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.Parameter;
import uk.emarte.regurgitator.core.ParameterPrototype;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.extensions.ContainsXpath;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static uk.emarte.regurgitator.core.CoreTypes.STRING;

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
        Map<String, String> namespaceUris = new HashMap<>();
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
