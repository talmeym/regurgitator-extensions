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
import uk.emarte.regurgitator.extensions.MeetsJsonSchemaBehaviour;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static uk.emarte.regurgitator.core.CoreTypes.STRING;

public class MeetsJsonSchemaBehaviourTest {
    @Test
    public void testBehaviour() throws RegurgitatorException {
        MeetsJsonSchemaBehaviour toTest = new MeetsJsonSchemaBehaviour();
        ParameterPrototype prototype = new ParameterPrototype("name", STRING, REPLACE);
        Parameter parameter = new Parameter(prototype, "{\"testId\":\"test1\"}");
        assertTrue(toTest.evaluate(parameter, new Message(null), "classpath:/json-schema-test.json", true));
        assertFalse(toTest.evaluate(parameter, new Message(null), "classpath:/json-schema-test.json", false));
    }
}
