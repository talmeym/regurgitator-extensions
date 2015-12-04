package com.emarte.regurgitator.extensions;

import com.emarte.regurgitator.core.*;
import com.jayway.jsonpath.*;

import static com.emarte.regurgitator.core.StringType.stringify;

public class ContainsJsonPath implements ConditionBehaviour {
	private static final Log log = Log.getLog(ContainsJsonPath.class);

	@Override
	public boolean evaluate(Parameter parameter, Message message, String conditionValue, boolean expectation) throws RegurgitatorException {
		boolean contains = false;

		if(parameter != null) {
			JsonPath jsonPath = JsonPath.compile(conditionValue);

			try {
				Object value = jsonPath.read(stringify(parameter));
				log.debug("Parameter " + (value != null ?  "satifies"  : "does not satisfy") + " json path '" + conditionValue + "'");
				contains = value != null;
			} catch (PathNotFoundException pnfe) {
				log.debug("Parameter does not satisfy json path '" + conditionValue + "'");
			}
		}

		return contains == expectation;
	}
}
