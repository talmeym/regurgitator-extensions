/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import org.junit.Before;
import org.junit.Test;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.extensions.JsonSchemaValidator;

import static org.junit.Assert.assertEquals;

public class JsonSchemaValidatorTest {
    private JsonSchemaValidator toTest;

    @Before
    public void setUp() throws RegurgitatorException {
        toTest = new JsonSchemaValidator("classpath:/json-schema-test.json");
    }

    @Test
    public void testSuccessfulValidation() throws RegurgitatorException {
        String value = "{\"testId\":\"test1\"}";
        assertEquals(value, toTest.process(value, new Message(null)));
    }

    @Test(expected = RegurgitatorException.class)
    public void testUnsuccessfulValidation_invalidName() throws RegurgitatorException {
        toTest.process("{\"productId\":\"prod1\"}", new Message(null));
    }

    @Test(expected = RegurgitatorException.class)
    public void testUnsuccessfulValidation_invalidType() throws RegurgitatorException {
        String value = "{\"testId\":1}";
        assertEquals(value, toTest.process(value, new Message(null)));
    }

    @Test(expected = RegurgitatorException.class)
    public void testMissingSchema() throws RegurgitatorException {
        toTest = new JsonSchemaValidator("classpath:/doesNotExist.json");
    }
}
