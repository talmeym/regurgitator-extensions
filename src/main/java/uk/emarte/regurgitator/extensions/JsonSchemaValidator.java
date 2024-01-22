/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import uk.emarte.regurgitator.core.Log;
import uk.emarte.regurgitator.core.Message;
import uk.emarte.regurgitator.core.RegurgitatorException;
import uk.emarte.regurgitator.core.ValueProcessor;

import java.io.IOException;
import java.util.Set;

import static uk.emarte.regurgitator.core.Log.getLog;
import static uk.emarte.regurgitator.core.StringType.stringify;
import static uk.emarte.regurgitator.extensions.JsonSchemaUtil.getSchema;

public class JsonSchemaValidator implements ValueProcessor {
    private static final Log log = getLog(JsonSchemaValidator.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private final String schemaPath;
    private final JsonSchema schema;

    public JsonSchemaValidator(String schemaPath) throws RegurgitatorException {
        this.schemaPath = schemaPath;
        schema = getSchema(schemaPath);
    }

    @Override
    public Object process(Object value, Message message) throws RegurgitatorException {
        try {
            JsonNode jsonNode = mapper.readTree(stringify(value));
            Set<ValidationMessage> errors = schema.validate(jsonNode);

            if(errors.size() == 0) {
                log.debug("Value successfully validated against json schema '{}'", schemaPath);
                return value;
            }

            throw new RegurgitatorException("Value did not validate against json schema + '" + schemaPath + "'");
        } catch (IOException e) {
            throw new RegurgitatorException("Error validating against json schema + '" + schemaPath + "'", e);
        }
    }
}
