/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.Message;
import com.emarte.regurgitator.core.Parameter;
import com.emarte.regurgitator.core.ParameterPrototype;
import com.emarte.regurgitator.core.RegurgitatorException;
import com.emarte.regurgitator.extensions.ContainsXpath;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static com.emarte.regurgitator.core.CoreTypes.STRING;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
