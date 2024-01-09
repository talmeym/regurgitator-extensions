/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.test;

import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.Parameter;
import uk.emarte.regurgitator.core.ParameterPrototype;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.extensions.ContainsJsonPathBehaviour;
import org.junit.Test;

import static uk.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static uk.emarte.regurgitator.core.CoreTypes.STRING;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ContainsJsonPathBehaviourTest {
    @Test
    public void testThis() throws RegurgitatorException {
        ContainsJsonPathBehaviour toTest = new ContainsJsonPathBehaviour();
        ParameterPrototype prototype = new ParameterPrototype("name", STRING, REPLACE);
        Parameter parameter = new Parameter(prototype, "{\"something\":{\"something\":\"this\"}}");
        assertTrue(toTest.evaluate(parameter, new Message(null), "$.something.something", true));
        assertFalse(toTest.evaluate(parameter, new Message(null), "$.something.something", false));
        assertFalse(toTest.evaluate(parameter, new Message(null), "$.something.something.something", true));
        assertTrue(toTest.evaluate(parameter, new Message(null), "$.something.something.something", false));
        assertFalse(toTest.evaluate(null, new Message(null), "$.something.something", true));
        assertTrue(toTest.evaluate(null, new Message(null), "$.something.something", false));
    }
}
