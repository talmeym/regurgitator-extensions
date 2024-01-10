/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import org.junit.Before;
import org.junit.Test;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.extensions.XmlSchemaValidator;

import static junit.framework.Assert.assertEquals;

public class XmlSchemaValidatorTest {
    private XmlSchemaValidator toTest;

    @Before
    public void setUp() throws RegurgitatorException {
        toTest = new XmlSchemaValidator("classpath:/xml-schema-test.xsd");
    }

    @Test
    public void testSuccessfulValidation() throws RegurgitatorException {
        String value = "<doc>A String</doc>";
        assertEquals(value, toTest.process(value, new Message(null)));
    }

    @Test(expected = RegurgitatorException.class)
    public void testUnsuccessfulValidation() throws RegurgitatorException {
        toTest.process("<dog>A String</dog>", new Message(null));
    }

    @Test(expected = RegurgitatorException.class)
    public void testMissingSchema() throws RegurgitatorException {
        toTest = new XmlSchemaValidator("classpath:/doesNotExist.xsd");
    }
}
