/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import uk.emarte.regurgitator.core.*;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.StringType.stringify;
import static com.jayway.jsonpath.JsonPath.compile;

public class ContainsJsonPathBehaviour implements ConditionBehaviour {
    private static final Log log = getLog(ContainsJsonPathBehaviour.class);

    @Override
    public boolean evaluate(Parameter parameter, Message message, String conditionValue, boolean expectation) {
        boolean contains = false;

        if(parameter != null) {
            JsonPath jsonPath = compile(conditionValue);

            try {
                Object value = jsonPath.read(stringify(parameter));
                log.debug("Parameter " + (value != null ?  "satisfies"  : "does not satisfy") + " json path '{}'", conditionValue);
                contains = value != null;
            } catch (PathNotFoundException pnfe) {
                log.debug("Parameter does not satisfy json path '{}'", conditionValue);
            }
        }

        return contains == expectation;
    }
}
