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
import uk.emarte.regurgitator.extensions.MeetsXmlSchemaBehaviour;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static uk.emarte.regurgitator.core.CoreTypes.STRING;

public class MeetsXmlSchemaBehaviourTest {
    @Test
    public void testBehaviour() throws RegurgitatorException {
        MeetsXmlSchemaBehaviour toTest = new MeetsXmlSchemaBehaviour();
        ParameterPrototype prototype = new ParameterPrototype("name", STRING, REPLACE);
        Parameter parameter = new Parameter(prototype, "<doc>A String</doc>");
        assertTrue(toTest.evaluate(parameter, new Message(null), "classpath:/xml-schema-test.xsd", true));
        assertFalse(toTest.evaluate(parameter, new Message(null), "classpath:/xml-schema-test.xsd", false));
        assertFalse(toTest.evaluate(parameter, new Message(null), "classpath:/xml-schema-import-test.xsd", true));
    }
}
