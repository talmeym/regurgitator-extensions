package com.emarte.regurgitator.test;

import com.emarte.regurgitator.core.*;
import com.emarte.regurgitator.extensions.ContainsJsonPath;
import org.junit.Test;

import static com.emarte.regurgitator.core.ConflictPolicy.REPLACE;
import static com.emarte.regurgitator.core.CoreTypes.STRING;
import static junit.framework.Assert.*;

public class ContainsJsonPathTest {

	@Test
	public void testThis() throws RegurgitatorException {
		ContainsJsonPath toTest = new ContainsJsonPath();
		ParameterPrototype prototype = new ParameterPrototype("name",
				STRING, REPLACE);
		Parameter parameter = new Parameter(prototype, "{\"something\":{\"something\":\"this\"}}");
		assertTrue(toTest.evaluate(parameter, new Message(null), "$.something.something", true));
		assertFalse(toTest.evaluate(parameter, new Message(null), "$.something.something", false));
		assertFalse(toTest.evaluate(parameter, new Message(null), "$.something.something.something", true));
		assertTrue(toTest.evaluate(parameter, new Message(null), "$.something.something.something", false));
		assertFalse(toTest.evaluate(null, new Message(null), "$.something.something", true));
		assertTrue(toTest.evaluate(null, new Message(null), "$.something.something", false));
	}

}
