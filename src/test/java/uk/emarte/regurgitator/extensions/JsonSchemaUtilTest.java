/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import org.junit.Test;
import uk.emarte.regurgitator.core.RegurgitatorException;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class JsonSchemaUtilTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUtil() throws RegurgitatorException, IOException {
        JsonSchema schema = JsonSchemaUtil.getSchema("classpath:/json-schema-test.json");

        String json = "{\"testId\":\"test1\"}";
        Set<ValidationMessage> errors = schema.validate(mapper.readTree(json));
        assertTrue(errors.isEmpty());
    }
}
