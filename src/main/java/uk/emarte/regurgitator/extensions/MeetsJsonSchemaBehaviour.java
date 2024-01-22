/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import uk.emarte.regurgitator.core.*;

import java.io.IOException;
import java.util.Set;

import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.StringType.stringify;

public class MeetsJsonSchemaBehaviour implements ConditionBehaviour {
    private static final Log log = getLog(MeetsJsonSchemaBehaviour.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean evaluate(Parameter parameter, Message message, String conditionValue, boolean expectation) throws RegurgitatorException {
        boolean validates = false;

        if(parameter != null) {
            try {
                JsonSchema schema = JsonSchemaUtil.getSchema(conditionValue);
                JsonNode jsonNode = mapper.readTree(stringify(parameter.getValue()));
                Set<ValidationMessage> errors = schema.validate(jsonNode);
                validates = errors.isEmpty();
            } catch (IOException e) {
                throw new RegurgitatorException("Exception validating against json schema: " + conditionValue, e);
            }
        }

        log.debug("Parameter " + (validates ?  "meets"  : "does not meets") + " json schema '{}'", conditionValue);
        return validates == expectation;
    }
}
